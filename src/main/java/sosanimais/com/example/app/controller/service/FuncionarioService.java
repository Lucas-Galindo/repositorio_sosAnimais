package sosanimais.com.example.app.controller.service;

import sosanimais.com.example.app.model.DAL.FuncionarioDAL;
import sosanimais.com.example.app.model.entity.Funcionario;

import java.util.List;

public class FuncionarioService {

    FuncionarioDAL repositorio;

    public boolean cadastro(Funcionario entidade){
        return repositorio.save(entidade);
    }

    public Funcionario getId(Long id){
        return repositorio.get(id);
    }

    public List<Funcionario> getAll(String filtro){
        return repositorio.get(filtro);
    }

    public boolean deletar(Funcionario entidade){
        return repositorio.delete(entidade);
    }

    public boolean atualizar(Funcionario entidade){
        return repositorio.update(entidade);
    }




}
