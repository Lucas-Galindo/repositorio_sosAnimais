package sosanimais.com.example.app.model.DAL;

import org.apache.el.stream.Stream;
import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.Endereco;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Empresa;


import java.sql.ResultSet;

import org.springframework.stereotype.Component;


@Repository
public abstract class EmpresaDAL implements IDAL<Empresa> {

    @Override
    public boolean save(Empresa entidade) {
        String sql = "";
        Empresa info = new Empresa(0, 0, "", "", "", "", "");
        ResultSet resultSet;

        String sqlAux = """
                    SELECT * FROM Empresa WHERE entidade.info_id = id
                """;
        resultSet = SingletonDB.getConexao().consultar(sqlAux);
        try {
            if (resultSet.next()) {
                long id = resultSet.getLong("id");

                resultSet.getLong("id");
            }

            sql = """ 
                    INSERT INTO Empresa(id, Capacidade,Cnpj, Nome, NomeFantasia,Rua,CEP,Numero,Descricao, telefone) VALUES ('#1','#2','#3','#4','#5','#6','#7','#8','#9','#10'); where info_id=1""";

            sql = sql.replace("#1", "" + entidade.getId());
            sql = sql.replace("#2", "" + entidade.getCapacidade());
            sql = sql.replace("#3", "" + entidade.getCnpj());
            sql = sql.replace("#4", "" + entidade.getNome());
            sql = sql.replace("#5", "" + entidade.getNomeFantasia());
            sql = sql.replace("#6", "" + entidade.getEndereco().getRua());
            sql = sql.replace("#7", "" + entidade.getEndereco().getCep());
            sql = sql.replace("#8", "" + entidade.getEndereco().getNumero());
            sql = sql.replace("#9", "" + entidade.getDescricao());
            sql = sql.replace("#10", "" + entidade.getTelefone());


            return SingletonDB.getConexao().manipular(sql);
        } catch (Exception e) {
            return false;
        }


    }

    @Override
    public boolean update(Empresa entidade) {

        String sql = """
                UPDATE Empresa SET capacidade='#2',Cnpj='#3',Nome='#4',NomeFantasia='#5',Rua='#6',CEP='#7',Numero='#8',Descricao='#9',Telefone='#10' WHERE id=1;
                """;

        sql = sql.replace("#2", "" + entidade.getCapacidade());
        sql = sql.replace("#3", "" + entidade.getCnpj());
        sql = sql.replace("#4", "" + entidade.getNome());
        sql = sql.replace("#5", "" + entidade.getNomeFantasia());
        sql = sql.replace("#6", "" + entidade.getEndereco().getRua());
        sql = sql.replace("#7", "" + entidade.getEndereco().getCep());
        sql = sql.replace("#8", "" + entidade.getEndereco().getNumero());
        sql = sql.replace("#9", "" + entidade.getDescricao());
        sql = sql.replace("#10", "" + entidade.getTelefone());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Empresa entidade) {

        return SingletonDB.getConexao().manipular("DELETE FROM Empresa WHERE info_id=" + entidade.getId());
    }


    @Override
    public Empresa get(Long mat) {
        int id = 0;
        Endereco end = null;
        String sql = "";
        ResultSet resultSet;
        Empresa infoAux = null;
        Endereco endAux;

        sql = "SELECT * FROM Empresa WHERE info_id=" + mat;
        resultSet = SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {
                infoAux = new Empresa(
                        resultSet.getInt("id"),
                        resultSet.getInt("capacidade"),
                        resultSet.getString("cnpj"),
                        resultSet.getString("nome"),
                        resultSet.getString("NomeFantasia"),
                        resultSet.getString("descricao"),
                        resultSet.getString("telefone")
                );

                return infoAux;
            }
        } catch (Exception e) {
            return null;
        }


        return null;
    }


    public Empresa buscarPorCep(String cep) {
        Empresa empresa = null;
        ResultSet resultSet;

        String sql = "SELECT * FROM Empresa WHERE CEP = '" + cep + "'";
        resultSet = SingletonDB.getConexao().consultar(sql);

        try {
            if (resultSet.next()) {
                empresa = new Empresa(
                        resultSet.getInt("id"),
                        resultSet.getInt("capacidade"),
                        resultSet.getString("cnpj"),
                        resultSet.getString("nome"),
                        resultSet.getString("NomeFantasia"),
                        resultSet.getString("descricao"),
                        resultSet.getString("telefone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return empresa;
    }
    public Empresa buscarPorCnpj(String cnpj) {
        String sql = "SELECT * FROM Empresa WHERE Cnpj = '" + cnpj + "'";
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

        try {
            if (resultSet.next()) {
                Empresa empresa = new Empresa(
                        resultSet.getInt("id"),
                        resultSet.getInt("Capacidade"),
                        resultSet.getString("Cnpj"),
                        resultSet.getString("Nome"),
                        resultSet.getString("NomeFantasia"),
                        resultSet.getString("Descricao"),
                        resultSet.getString("telefone")
                );
                return empresa;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Empresa buscarUnicaEmpresa() {
        String sql = "SELECT * FROM Empresa LIMIT 1";
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

        try {
            if (resultSet.next()) {
                return new Empresa(
                        resultSet.getInt("id"),
                        resultSet.getInt("capacidade"),
                        resultSet.getString("cnpj"),
                        resultSet.getString("nome"),
                        resultSet.getString("NomeFantasia"),
                        resultSet.getString("descricao"),
                        resultSet.getString("telefone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }




}