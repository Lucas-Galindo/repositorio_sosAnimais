package sosanimais.com.example.app.controller.service;

import sosanimais.com.example.app.model.DAL.DoadorDAL;
import sosanimais.com.example.app.model.entity.Doador;

import java.util.List;

public class DoadorService {

    DoadorDAL repositorio = new DoadorDAL();

    public boolean cadastro(Doador entidade){
        return repositorio.save(entidade);
    }
    public Doador getId(Long mat){
        return repositorio.get(mat);
    }
    public List<Doador> getAll(String filtro)  {
        return repositorio.get(filtro);
    }
    public boolean deletar(Doador entidade){
        return repositorio.delete(entidade);
    }
    public boolean atualizar(Doador entidade){
        return repositorio.update(entidade);
    }

    public Doador getPessoaId(Long id){ return repositorio.findByPessoaId(id);}




}
