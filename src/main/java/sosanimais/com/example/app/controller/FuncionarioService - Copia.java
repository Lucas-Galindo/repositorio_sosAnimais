package sosanimais.com.example.app.controller.services;


import org.springframework.http.ResponseEntity;
import sosanimais.com.example.app.model.db.IDAO;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Categoria;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.entity.Pessoa;
import sosanimais.com.example.app.model.entity.Produto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioService implements IDAO<Funcionario>{



    PessoaService pessoaSerivce;

    
    @Override
    public boolean save(Pessoa pessoa, Funcionario entidade){

        String sql= """ INSERT INTO funcionario(pess_id,func_matricula, func_login, func_senha) VALUES ('#1','#2','#3','#4'); """;
        

        sql=sql.replace("#1","" +pessoa.getId());
        sql=sql.replace("#2",""+entidade.getMatricula());
        sql=sql.replace("#3", entidade.getLogin());
        sql=sql.replace("#4",entidade.getSenha());
     
        return SingletonDB.getConexao().manipular(sql);

    }

    @Override
    public boolean update(Funcionario entidade) {

        String sql= """
            UPDATE funcionario SET func_matricula='#1',func_login='#2',func_senha='#3' WHERE func_matricula=#4;
            """;
        sql = sql.replace("#1", entidade.getMatricula());
        sql = sql.replace("#2", entidade.getLogin());
        sql = sql.replace("#3", entidade.getSenha());
        sql = sql.replace("#4", "" + entidade.getId());
       
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Pessoa pessoa, Funcionario entidade) {
        //SingletonDB.getConexao().manipular("DELETE FROM pessoa WHERE pess_id="+entidade.getId());
        return SingletonDB.getConexao().manipular("DELETE FROM funcionario WHERE func_matricula="+entidade.getMatricula());
    }

    @Override
    public Funcionario get(Pessoa pessoa, int mat) {
        Funcionario func=null;
        String sql = "";

        //fazer um inner join
        /*if(pessoa!=null)
            sql="SELECT * FROM pessoa WHERE pess_id="+pessoa.getId()+"AND func_matricula="+mat;
        else    
            sql="SELECT * FROM funcionario WHERE func_matricula="+mat;*/

        /*
         * SELECT testproduct_id, product_name, category_name
            FROM testproducts
            INNER JOIN categories ON testproducts.category_id = categories.category_id; 
         */

         // nao e func id, pode ser o pess
        sql = "SELECT * FROM funcionario INNER JOIN pessoa ON funcionario.pess_id = pessoa.pess_id";
       
        ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {
                func = new Funcionario(resultSet.getLong("pess_id"),
                        resultSet.getInt("func_matricula"),
                        resultSet.getString("func_login"),
                        resultSet.getString("func_senha"));
            }
        }catch (Exception e){e.printStackTrace();}
        return func;
    }

    @Override
    public List<Funcionario> get(String filtro) {
        List <Funcionario> pedidoList=new ArrayList<>();
        Funcionario pedido=null;
        String sql="SELECT * FROM funcionario ";
        if(!filtro.isEmpty())
            sql+=" WHERE "+filtro;
        try{
           /* ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
            while(resultSet.next())
            {
                
            }
            String sql2="SELECT * FROM item WHERE ped_id="+pedido.getId();
            ResultSet resultSet2=SingletonDB.getConexao().consultar(sql2);
            while(resultSet2.next()){
                Produto produto=new ProdutoDAL().get(resultSet2.getInt("prod_id"));
                produto.setValor(resultSet2.getDouble("it_valor"));
                pedido.addItem(produto,resultSet2.getInt("it_quant"));
            }*/
        }
       // catch(Exception e ){e.printStackTrace();}
        return pedidoList;
    }
    
}
