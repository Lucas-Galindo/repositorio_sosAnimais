package sosanimais.com.example.app.controller.service;

import sosanimais.com.example.app.model.DAL.AdotanteDAL;
import sosanimais.com.example.app.model.entity.Adotante;

import java.util.List;

public class AdotanteService {

    AdotanteDAL repositorio = new AdotanteDAL();

    public boolean cadastro(Adotante entidade){
        return repositorio.save(entidade);
    }
    public Adotante getId(Long mat){
        return repositorio.get(mat);
    }
    public List<Adotante> getAll(String filtro)  {
        return repositorio.get(filtro);
    }
    public boolean deletar(Adotante entidade){
        return repositorio.delete(entidade);
    }
    public boolean atualizar(Adotante entidade){
        return repositorio.update(entidade);
    }

    public Adotante getPessoaId(Long id){ return repositorio.findByPessoaId(id);}




}
