package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.PessoaInformacao;

public class Pessoa {
<<<<<<< HEAD
    private Long id;
    private PessoaInformacao pessoa;

    public Pessoa(Long id, PessoaInformacao pessoa) {
        this.id = id;
        this.pessoa = pessoa;
    }
    public Pessoa(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
=======
    private int id;
    private PessoaInformacao pessoa;

    public Pessoa(int id, PessoaInformacao pessoa) {
        this.id = id;
        this.pessoa = pessoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
>>>>>>> c79675591135cd43030da201dac6a938754b568b
        this.id = id;
    }

    public PessoaInformacao getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaInformacao pessoa) {
        this.pessoa = pessoa;
    }
}
