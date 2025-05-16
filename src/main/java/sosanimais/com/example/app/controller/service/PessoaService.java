package sosanimais.com.example.app.controller.service;

import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.PessoaDAL;
import sosanimais.com.example.app.model.entity.Pessoa;

@Service
public class PessoaService {
    PessoaDAL repositorio;
    public Pessoa getById(Long id){
        return repositorio.get(id);
    }


}
