package sosanimais.com.example.app.controller.service;

import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.AdotanteDAL;
import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Adotante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdotanteService {

    AdotanteDAL repositorio = new AdotanteDAL();

    public boolean cadastro(Adotante entidade){
        return repositorio.save(entidade);
    }

    public Adotante getId(Long mat){
        return repositorio.get(mat);
    }

    public List<Adotante> getAll(String filtro)  {
        return repositorio.get(filtro);
    }

    public boolean deletar(Adotante entidade){
        return repositorio.delete(entidade);
    }

    public boolean atualizar(Adotante entidade){
        return repositorio.update(entidade);
    }

    public boolean atualizarAdocao(Long matricula, Long adocaoCod) {
        try {
            Adotante adotante = getId(matricula);
            if (adotante != null) {
                adotante.setAdocaoAnimalAdotaCod(adocaoCod);
                return repositorio.update(adotante);
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar adoção do adotante: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removerAdocao(Long matricula) {
        try {
            Adotante adotante = getId(matricula);
            if (adotante != null) {
                adotante.setAdocaoAnimalAdotaCod(null);
                return repositorio.update(adotante);
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao remover adoção do adotante: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private PessoaInformacao extrairPessoaInformacaoDoResultSet(ResultSet rs) throws SQLException {
        if (rs == null) return null;
        return new PessoaInformacao(
                rs.getString("pess_nome"),
                rs.getString("pess_cpf"),
                rs.getString("pess_telefone"),
                rs.getString("pess_email")
        );
    }

    // Seus outros métodos (save, update, delete, get por ID) também precisariam
    // ser ajustados para não usar PreparedStatement, se essa for a diretriz para todos.
    // Lembre-se que isso os tornaria igualmente vulneráveis.

    // Exemplo de como o get(Long id) ficaria, sem PreparedStatement (também vulnerável se id pudesse ser manipulado):

    public Adotante get(Long matriculaAdotante) {
        Adotante adotante = null;
        // ATENÇÃO: VULNERÁVEL a SQL Injection se matriculaAdotante não for estritamente um número.
        String sql = "SELECT a.adotante_matricula, a.usu_id, " +
                "p.pess_nome, p.pess_cpf, p.pess_telefone, p.pess_email " +
                "FROM adotante a " +
                "JOIN pessoa p ON a.usu_id = p.pess_id " + // Ajuste p.pess_id
                "WHERE a.adotante_matricula = " + matriculaAdotante;

        ResultSet rs = null;
        try {
            rs = SingletonDB.getConexao().consultar(sql);
            if (rs != null && rs.next()) {
                PessoaInformacao pessoaInfo = extrairPessoaInformacaoDoResultSet(rs);
                adotante = new Adotante(
                        rs.getLong("usu_id"),
                        pessoaInfo,
                        rs.getInt("adotante_matricula")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar adotante por matrícula: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Gerenciamento de ResultSet
        }
        return adotante;
    }

}
