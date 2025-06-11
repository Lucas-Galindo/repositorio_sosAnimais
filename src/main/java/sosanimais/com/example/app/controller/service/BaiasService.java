package sosanimais.com.example.app.controller.service;


import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.BaiasDAL;
import sosanimais.com.example.app.model.entity.Baias;
import sosanimais.com.example.app.model.entity.Baias;

import java.util.List;
@Service
public class BaiasService {


    private BaiasDAL repositorio = new BaiasDAL();
    public BaiasService(){}

    // CRUD BASICO
    public boolean cadastro(Baias entidade){return repositorio.save(entidade);}
    public Baias getId(Long id){return repositorio.get(id);}
    public List<Baias> getAll(String filtro){return repositorio.get(filtro);}
    public boolean deletar(Baias entidade){return repositorio.delete(entidade);}
    public boolean atualizar(Baias entidade){return repositorio.update(entidade);}


    public List<Baias> getAllByFilter(String filtro) {return repositorio.getAllByBaia(filtro);}

    public Baias getNomeBaia(String nome) {return repositorio.findByNome(nome);}

    public Baias getBaiaByIdNome(String categoria, Long id) {return repositorio.findBaiaByIdNome(categoria, id);}

}
