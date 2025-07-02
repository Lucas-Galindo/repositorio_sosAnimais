package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.DAL.PessoaDAL;
import sosanimais.com.example.app.model.PessoaInformacao;

import java.util.List;

public class Pessoa {

    private Long id;
    private PessoaInformacao pessoa;

    public Pessoa(Long id, PessoaInformacao pessoa) {
        this.id = id;
        this.pessoa = pessoa;
    }
    public Pessoa(Long id){
        this.id = id;
    }
    public Pessoa(){
        this(0L,null);
    }

    public void setPessoa(PessoaInformacao pessoa) {
        this.pessoa = pessoa;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PessoaInformacao getPessoa(){return this.pessoa;}


    public PessoaDAL repositorio = new PessoaDAL();


    // CRUD BASICO
    public boolean cadastro(Pessoa entidade){
        return repositorio.save(entidade);
    }
    public Pessoa getId(Long id){return repositorio.get(id);}
    //public List<Pessoa> getAll(String filtro){
        //return repositorio.get(filtro);
    //}
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
