package sosanimais.com.example.app.model.service;

import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.entity.Pessoa;
import sosanimais.com.example.app.model.PessoaInformacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioService implements IDAL<Funcionario>{

    @Override
    public boolean save(Funcionario entidade) {
        return false;
    }

    //Salva um funcionário junto com o vínculo à pessoa
    @Override
    public boolean save(Pessoa pessoa, Funcionario entidade) throws SQLException{
        String sql = """
            INSERT INTO funcionario(pess_id, func_matricula, func_login, func_senha)
            VALUES ('#1', '#2', '#3', '#4');
        """;

        sql = sql.replace("#1", "" + pessoa.getId());
        sql = sql.replace("#2", "" + entidade.getMatricula());
        sql = sql.replace("#3", entidade.getLogin());
        sql = sql.replace("#4", entidade.getSenha());

        return SingletonDB.getConnection().manipular(sql);
    }

    //Atualiza um funcionário
    @Override
    public boolean update(Funcionario entidade){
        String sql = """
            UPDATE funcionario
            SET func_matricula = '#1', func_login = '#2', func_senha = '#3'
            WHERE pess_id = #4;
        """;

        sql = sql.replace("#1", "" + entidade.getMatricula());
        sql = sql.replace("#2", entidade.getLogin());
        sql = sql.replace("#3", entidade.getSenha());
        sql = sql.replace("#4", "" + entidade.getId());

        return SingletonDB.getConnection().manipular(sql);
    }

    @Override
    public boolean delete(Funcionario entidade) {
        return false;
    }

    @Override
    public Funcionario get(int id) {
        return null;
    }

    //Deleta um funcionário
    @Override
    public boolean delete(Pessoa pessoa, Funcionario entidade){
        return SingletonDB.getConnection()
                .manipular("DELETE FROM funcionario WHERE func_matricula = " + entidade.getMatricula());
    }

    //Retorna um funcionário específico
    @Override
    public Funcionario get(Pessoa pessoa, int mat){
        Funcionario func = null;

        String sql = "SELECT * FROM funcionario f INNER JOIN pessoa p ON f.pess_id = p.pess_id " +
                "WHERE f.func_matricula = " + mat;

        try(ResultSet rs = SingletonDB.getConnection().consultar(sql)){
            if (rs.next()) {
                PessoaInformacao info = new PessoaInformacao(
                        rs.getString("pess_nome"),
                        rs.getString("pess_cpf"),
                        rs.getString("pess_email"),
                        rs.getString("pess_telefone")
                );
                func = new Funcionario(
                        rs.getLong("pess_id"),
                        info,
                        rs.getInt("func_matricula"),
                        rs.getString("func_login"),
                        rs.getString("func_senha")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return func;
    }

    //Retorna uma lista de funcionários com filtro opcional
    @Override
    public List<Funcionario> get(String filtro){
        List<Funcionario> lista = new ArrayList<>();

        String sql = "SELECT * FROM funcionario f INNER JOIN pessoa p ON f.pess_id = p.pess_id";
        if (filtro != null && !filtro.isEmpty()){
            sql += " WHERE " + filtro;
        }

        try(ResultSet rs = SingletonDB.getConnection().consultar(sql)){
            while (rs.next()) {
                PessoaInformacao info = new PessoaInformacao(
                        rs.getString("pess_nome"),
                        rs.getString("pess_cpf"),
                        rs.getString("pess_email"),
                        rs.getString("pess_telefone")
                );
                Funcionario f = new Funcionario(
                        rs.getLong("pess_id"),
                        info,
                        rs.getInt("func_matricula"),
                        rs.getString("func_login"),
                        rs.getString("func_senha")
                );
                lista.add(f);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    //Buscando funcionário por login e senha (usa no LoginServelet)
    public Funcionario buscarLoginPorSenha(String login, String senha){
        Funcionario func = null;

        String sql = "SELECT * FROM funcionario f " +
                "INNER JOIN pessoa p ON f.pess_id = p.pess_id " +
                "WHERE f.func_login = '" + login + "' AND f.func_senha = '" + senha + "'";

        try(ResultSet rs = SingletonDB.getConnection().consultar(sql)){
            if(rs.next()){
                PessoaInformacao info = new PessoaInformacao(
                        rs.getString("pess_nome"),
                        rs.getString("pess_cpf"),
                        rs.getString("pess_email"),
                        rs.getString("pess_telefone")
                );
                func = new Funcionario(
                        rs.getLong("pess_id"),
                        info,
                        rs.getInt("func_matricula"),
                        rs.getString("func_login"),
                        rs.getString("func_senha")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return func;
    }
}
