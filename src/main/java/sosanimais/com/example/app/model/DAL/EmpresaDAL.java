package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.Endereco;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Empresa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpresaDAL implements IDAL<Empresa> {

    private Empresa montarEmpresa(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        if (rs.getString("emp_endereco") != null) {
            // A string completa do endereço do banco é colocada no campo 'rua' do objeto Endereco.
            // O JavaScript da página 'Sobre Nós' precisará saber disso para exibir corretamente.
            endereco.setRua(rs.getString("emp_endereco"));
        }

        // Usa o construtor de 8 argumentos (sem historia, pois não vem do DB)
        return new Empresa(
                rs.getInt("emp_id"),
                rs.getInt("emp_capacidade"),
                rs.getString("emp_cnpj"),
                rs.getString("emp_nome"),
                rs.getString("emp_nomefantasia"),
                endereco,
                rs.getString("emp_descricao"),
                rs.getString("emp_telefone")
        );
    }

    @Override
    public boolean save(Empresa entidade) {
        Empresa empresaExistente = buscarUnicaEmpresa();

        if (empresaExistente != null) {
            entidade.setId(empresaExistente.getId());
            return update(entidade);
        }

        String enderecoCompletoParaSalvar = "";
        if (entidade.getEndereco() != null) {
            StringBuilder sb = new StringBuilder();
            if (entidade.getEndereco().getRua() != null && !entidade.getEndereco().getRua().isEmpty()) {
                sb.append(entidade.getEndereco().getRua().replace("'", "''"));
            }
            if (entidade.getEndereco().getNumero() != 0) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(entidade.getEndereco().getNumero());
            }
            // Os campos bairro, cidade, estado não existem na classe Endereco.java fornecida.
            // Se o formulário os enviar, o Jackson os ignorará ao criar o objeto Endereco no controller.
            // Portanto, só podemos usar rua, numero, cep e complemento aqui.
            if (entidade.getEndereco().getCep() != null && !entidade.getEndereco().getCep().isEmpty()) {
                if (sb.length() > 0) sb.append(" - ");
                sb.append("CEP: ").append(entidade.getEndereco().getCep().replace("'", "''"));
            }
            if (entidade.getEndereco().getComplemento() != null && !entidade.getEndereco().getComplemento().isEmpty()) {
                if (sb.length() > 0) sb.append(" ");
                sb.append("(").append(entidade.getEndereco().getComplemento().replace("'", "''")).append(")");
            }
            String tempEndereco = sb.toString().trim();
            if (tempEndereco.startsWith(",") || tempEndereco.startsWith("-")) {
                enderecoCompletoParaSalvar = tempEndereco.substring(1).trim();
            } else {
                enderecoCompletoParaSalvar = tempEndereco;
            }
        }

        // emp_historia não é guardada no banco conforme a CREATE TABLE Empresa
        String sql = "INSERT INTO Empresa (emp_nome, emp_nomefantasia, emp_descricao, emp_endereco, emp_telefone, emp_capacidade, emp_cnpj) " +
                "VALUES ('" + (entidade.getNome() != null ? entidade.getNome().replace("'", "''") : "") + "', '" +
                (entidade.getNomeFantasia() != null ? entidade.getNomeFantasia().replace("'", "''") : "") + "', '" +
                (entidade.getDescricao() != null ? entidade.getDescricao().replace("'", "''") : "") + "', '" +
                enderecoCompletoParaSalvar + "', '" +
                (entidade.getTelefone() != null ? entidade.getTelefone().replace("'", "''") : "") + "', " +
                entidade.getCapacidade() + ", '" +
                (entidade.getCnpj() != null ? entidade.getCnpj().replace("'", "''") : "") + "')";

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean update(Empresa entidade) {
        String enderecoCompletoParaSalvar = "";
        if (entidade.getEndereco() != null) {
            StringBuilder sb = new StringBuilder();
            if (entidade.getEndereco().getRua() != null && !entidade.getEndereco().getRua().isEmpty()) {
                sb.append(entidade.getEndereco().getRua().replace("'", "''"));
            }
            if (entidade.getEndereco().getNumero() != 0) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(entidade.getEndereco().getNumero());
            }
            if (entidade.getEndereco().getCep() != null && !entidade.getEndereco().getCep().isEmpty()) {
                if (sb.length() > 0) sb.append(" - ");
                sb.append("CEP: ").append(entidade.getEndereco().getCep().replace("'", "''"));
            }
            if (entidade.getEndereco().getComplemento() != null && !entidade.getEndereco().getComplemento().isEmpty()) {
                if (sb.length() > 0) sb.append(" ");
                sb.append("(").append(entidade.getEndereco().getComplemento().replace("'", "''")).append(")");
            }
            String tempEndereco = sb.toString().trim();
            if (tempEndereco.startsWith(",") || tempEndereco.startsWith("-")) {
                enderecoCompletoParaSalvar = tempEndereco.substring(1).trim();
            } else {
                enderecoCompletoParaSalvar = tempEndereco;
            }
        }

        // emp_historia não é atualizada no banco
        String sql = "UPDATE Empresa SET emp_nome='" + (entidade.getNome() != null ? entidade.getNome().replace("'", "''") : "") + "', " +
                "emp_nomefantasia='" + (entidade.getNomeFantasia() != null ? entidade.getNomeFantasia().replace("'", "''") : "") + "', " +
                "emp_descricao='" + (entidade.getDescricao() != null ? entidade.getDescricao().replace("'", "''") : "") + "', " +
                "emp_endereco='" + enderecoCompletoParaSalvar + "', " +
                "emp_telefone='" + (entidade.getTelefone() != null ? entidade.getTelefone().replace("'", "''") : "") + "', " +
                "emp_capacidade=" + entidade.getCapacidade() + ", " +
                "emp_cnpj='" + (entidade.getCnpj() != null ? entidade.getCnpj().replace("'", "''") : "") + "' " +
                "WHERE emp_id=" + entidade.getId();

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Empresa entidade) {
        String sql = "DELETE FROM Empresa WHERE emp_id=" + entidade.getId();
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public Empresa get(Long id) {
        String sql = "SELECT emp_id, emp_nome, emp_nomefantasia, emp_descricao, emp_endereco, emp_telefone, emp_capacidade, emp_cnpj FROM Empresa WHERE emp_id=" + id;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs != null && rs.next()) {
                return montarEmpresa(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empresa por ID ("+ id +"): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    @Override
    public List<Empresa> get(String filtro) {
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT emp_id, emp_nome, emp_nomefantasia, emp_descricao, emp_endereco, emp_telefone, emp_capacidade, emp_cnpj FROM Empresa WHERE emp_nome ILIKE '%" + filtro.replace("'", "''") + "%'";
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs != null && rs.next()) {
                empresas.add(montarEmpresa(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empresas por filtro ("+ filtro +"): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return empresas;
    }

    public Empresa buscarPorCep(String cep) {
        String sql = "SELECT emp_id, emp_nome, emp_nomefantasia, emp_descricao, emp_endereco, emp_telefone, emp_capacidade, emp_cnpj FROM Empresa WHERE emp_endereco LIKE '%" + cep.replace("'", "''") + "%'";
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs != null && rs.next()) {
                return montarEmpresa(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empresa por CEP ("+ cep +"): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    public Empresa buscarPorCnpj(String cnpj) {
        String sql = "SELECT emp_id, emp_nome, emp_nomefantasia, emp_descricao, emp_endereco, emp_telefone, emp_capacidade, emp_cnpj FROM Empresa WHERE emp_cnpj = '" + cnpj.replace("'", "''") + "'";
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs != null && rs.next()) {
                return montarEmpresa(rs);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar empresa por CNPJ ("+ cnpj +"): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    public Empresa buscarUnicaEmpresa() {
        String sql = "SELECT emp_id, emp_nome, emp_nomefantasia, emp_descricao, emp_endereco, emp_telefone, emp_capacidade, emp_cnpj FROM Empresa LIMIT 1";
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs != null && rs.next()) {
                return montarEmpresa(rs);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar única empresa: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }
}
