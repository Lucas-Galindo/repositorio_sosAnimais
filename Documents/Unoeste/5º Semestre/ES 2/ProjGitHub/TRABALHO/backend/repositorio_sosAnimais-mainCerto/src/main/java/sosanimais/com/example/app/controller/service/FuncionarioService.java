package sosanimais.com.example.app.controller.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.Security_JWT.FuncionarioRole;
import sosanimais.com.example.app.model.DAL.FuncionarioDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class FuncionarioService {


    FuncionarioDAL repositorio = new FuncionarioDAL();

    public boolean cadastro(Funcionario entidade){
        return repositorio.save(entidade);
    }

    public Funcionario getId(Long mat){
        return repositorio.get(mat);
    }

    public List<Funcionario> getAll(String filtro)  {
        return repositorio.get(filtro);
    }

    public boolean deletar(Funcionario entidade){
        if ("ADMIN".equals(entidade.getRole()) && contarAdmins() <= 1) {
            return false; //Impede a exclusão caso tenha só um ADMIN
        }
        return repositorio.delete(entidade);
    }

    public boolean atualizar(Funcionario entidade){
        return repositorio.update(entidade);
    }

    
    public UserDetails buscaLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM funcionario WHERE func_login = '" + login + "'";
        ResultSet acesso = SingletonDB.getConexao().consultar(sql);
        
        try {
            if (acesso != null && acesso.next()) {
                String loginF = acesso.getString("func_login");
                String senha = acesso.getString("func_senha");
                String role = acesso.getString("func_role");
                return new Funcionario(loginF, senha, FuncionarioRole.valueOf(role.toUpperCase()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int contarAdmins() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM funcionario WHERE UPPER(func_role) = 'ADMIN'";
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs != null && rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }



}
