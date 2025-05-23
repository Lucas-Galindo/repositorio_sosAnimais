package sosanimais.com.example.app.controller.service;

import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.PessoaDAL;
import sosanimais.com.example.app.model.entity.Pessoa;

import java.util.List;

@Service
public class PessoaService {

    private PessoaDAL repositorio = new PessoaDAL();
    public PessoaService(){}

    // CRUD BASICO
    public boolean cadastro(Pessoa entidade){
        return repositorio.save(entidade);
    }
    public Pessoa getId(Long id){return repositorio.get(id);}
    public List<Pessoa> getAll(String filtro){
        return repositorio.get(filtro);
    }
    public boolean deletar(Pessoa entidade){
        return repositorio.delete(entidade);
    }
    public boolean atualizar(Pessoa entidade){
        return repositorio.update(entidade);
    }

    public Pessoa getCpf(String cpf){ return repositorio.findByCPF(cpf);}
    public Pessoa getEmail(String email){ return repositorio.findByEmail(email);}
    public Pessoa getNomePessoa(String nome){ return repositorio.findByNome(nome);}

}
