package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.FuncionarioDAL;
import sosanimais.com.example.app.model.DAL.PessoaDAL;
import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.entity.Pessoa;

import java.sql.ResultSet;
import java.util.List;

@Service
public class PessoaService {


    private PessoaDAL repositorio = new PessoaDAL();

    public PessoaService(){

    }
    public boolean cadastro(Pessoa entidade){
        return repositorio.save(entidade);
    }

    public Pessoa getId(Long id){
        return repositorio.get(id);
    }

    public List<Pessoa> getAll(String filtro){
        return repositorio.get(filtro);
    }
    public boolean deletar(Pessoa entidade){
        return repositorio.delete(entidade);
    }
    public boolean atualizar(Pessoa entidade){
        return repositorio.update(entidade);
    }

    public PessoaInformacao getPessoaInfo(ResultSet resultSet) {
        try {
            return new PessoaInformacao(
                    resultSet.getString("pess_nome"),
                    resultSet.getString("pess_cpf"),
                    resultSet.getString("pess_telefone"),
                    resultSet.getString("pess_email")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }














}
