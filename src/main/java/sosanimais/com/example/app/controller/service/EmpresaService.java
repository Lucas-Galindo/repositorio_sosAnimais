package sosanimais.com.example.app.controller.service;

import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.EmpresaDAL;
import sosanimais.com.example.app.model.entity.informacao;

import java.util.List;

@Service
public class EmpresaService {

    EmpresaDAL repositorio = new EmpresaDAL();

    public boolean cadastro(informacao entidade){
        return repositorio.save(entidade);
    }

    public EmpresaDAL getId(Long id){
        return repositorio.get(id);
    }

    public List<EmpresaDAL> getAll(String filtro){
        return repositorio.get(filtro);
    }

    public boolean deletar(EmpresaService entidade){
        return repositorio.delete(entidade);
    }

    public boolean atualizar(EmpresaService entidade){
        return repositorio.update(entidade);
    }




}
