package sosanimais.com.example.app.model.DAL;
import sosanimais.com.example.app.model.Transferencia;
import sosanimais.com.example.app.model.entity.Baias;
import sosanimais.com.example.app.model.db.SingletonDB;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BaiasDAL {

    public BaiasDAL(){
        super();
    }


    /*
    *   private Long id;
        private int quantidadeAnimais;
        private String nome;
        private String categoria;
    *
    * */

    public boolean save(Baias entidade) {

        String sql =  sql = """
                    INSERT INTO baia(baia_qtde,baia_nome,baia_categoria) VALUES ('#1','#2','#3');
                    """;

        sql = sql.replace("#1", "" + entidade.getQuantidadeAnimais());
        sql = sql.replace("#2", entidade.getNome());
        sql = sql.replace("#3", entidade.getCategoria());

        return SingletonDB.getConexao().manipular(sql);
    }


    public boolean update(Baias entidade) {

        String sql = """
                UPDATE baia SET baia_qtde = #2', baia_nome = '#3', baia_categoria = '#4' WHERE baia_id = #1;
                """;
        sql = sql.replace("#2",""+ entidade.getQuantidadeAnimais());
        sql = sql.replace("#3", entidade.getNome());
        sql = sql.replace("#4", entidade.getCategoria());
        sql = sql.replace("#1",""+ entidade.getId());

        return SingletonDB.getConexao().manipular(sql);

    }

    public boolean updateQtde(Long id){
        String sql = """
                UPDATE UPDATE baia SET baia_qtde = baia_qtde + 1  WHERE baia_id = #1;
                """;
        sql = sql.replace("#1",""+ id);
        return SingletonDB.getConexao().manipular(sql);
    }


    public boolean delete(Baias entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM baia WHERE baia_id=" + entidade.getId());
    }

    public Baias get(Long id) {

        String sql;
        ResultSet resultSet;
//        int aux;
//        aux = Math.toIntExact(mat);

        sql = "SELECT * FROM baia WHERE baia_id =" + Math.toIntExact(id);
        resultSet = SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {

                return new Baias(
                        resultSet.getLong("baia_id"),
                        resultSet.getInt("baia_qtde"),
                        resultSet.getString("baia_nome"),
                        resultSet.getString("baia_categoria")
                );

            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


    public List<Baias> get(String filtro) {

        List<Baias> lista = new ArrayList<>();
        ResultSet resultSet;

        try {
            String sql = "SELECT * FROM baia";

            if(!filtro.isEmpty())
                sql+=" WHILE"+filtro;

            resultSet = SingletonDB.getConexao().consultar(sql);

            while (resultSet.next()) {
                lista.add(
                     new Baias(
                            resultSet.getLong("baia_id"),
                            resultSet.getInt("baia_qtde"),
                            resultSet.getString("baia_nome"),
                            resultSet.getString("baia_categoria")
                    )
                );

            }

            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


    public List<Baias> getAllByBaia(String filtro){
        List<Baias> lista = new ArrayList<>();

        String sql = "SELECT * FROM baia WHERE baia_categoria = '" + filtro + "'"; // Adicionadas aspas simples
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

        try{
            while(resultSet.next()){
                lista.add(
                        new Baias(
                                resultSet.getLong("baia_id"),
                                resultSet.getInt("baia_qtde"),
                                resultSet.getString("baia_nome"),
                                resultSet.getString("baia_categoria")
                        )
                );
            }
            return lista;

        }catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }


    public Baias findByNome(String nome){
        String sql = "SELECT * FROM baia WHERE baia_nome = "+nome+"';";
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

        try{
            if(resultSet.next()){
                return new Baias(
                        resultSet.getLong("baia_id"),
                        resultSet.getInt("baia_qtde"),
                        resultSet.getString("baia_nome"),
                        resultSet.getString("baia_categoria")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Baias findBaiaByIdNome(String cat, Long id){
        String sql = "SELECT * FROM baia WHERE baia_categoria = '" + cat + "' AND baia_id = " + id;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

        try{
            if(resultSet.next()){
                return new Baias(
                        resultSet.getLong("baia_id"),
                        resultSet.getInt("baia_qtde"),
                        resultSet.getString("baia_nome"),
                        resultSet.getString("baia_categoria")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
