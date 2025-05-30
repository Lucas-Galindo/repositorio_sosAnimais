package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.PessoaInformacao;

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
}
