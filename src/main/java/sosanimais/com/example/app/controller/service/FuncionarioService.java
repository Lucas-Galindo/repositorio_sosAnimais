package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.FuncionarioDAL;
import sosanimais.com.example.app.model.entity.Funcionario;

import java.sql.SQLException;
import java.util.List;

@Service
public class FuncionarioService {


    FuncionarioDAL repositorio = new FuncionarioDAL();

    public boolean cadastro(Funcionario entidade){return repositorio.save(entidade);}
    public Funcionario getId(Long mat){return repositorio.get(mat);}
    public List<Funcionario> getAll(String filtro) {return repositorio.get(filtro);}
    public boolean deletar(Funcionario entidade){
        return repositorio.delete(entidade);
    }
    public boolean atualizar(Funcionario entidade){
        return repositorio.update(entidade);
    }

    public Funcionario getPessoaId(Long id){ return repositorio.findByPessoaId(id);}



}
