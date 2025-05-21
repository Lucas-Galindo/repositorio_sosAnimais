package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.EmpresaDAL;
import sosanimais.com.example.app.model.entity.Empresa;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaDAL repositorio;

    public boolean cadastro(Empresa entidade){
        return repositorio.save(entidade);
    }

    public Empresa getId(Long id){
        return repositorio.get(id);
    }

    public boolean deletar(Empresa entidade){
        return repositorio.delete(entidade);
    }

    public boolean atualizar(Empresa entidade){
        return repositorio.update(entidade);
    }
}
