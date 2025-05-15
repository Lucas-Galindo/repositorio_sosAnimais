package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.PessoaInformacao;

public class Funcionario extends Pessoa {
    private int matricula;
    private String login;
    private String senha;
<<<<<<< HEAD
    

    public Funcionario(Long id, PessoaInformacao pessoa, int matricula, String login, String senha) {
=======

    public Funcionario(int id, PessoaInformacao pessoa, int matricula, String login, String senha) {
>>>>>>> c79675591135cd43030da201dac6a938754b568b
        super(id, pessoa);
        this.matricula = matricula;
        this.login = login;
        this.senha = senha;
    }
<<<<<<< HEAD
    public Funcionario(){
        this(0L,null,0,"","");
    }
=======
>>>>>>> c79675591135cd43030da201dac6a938754b568b

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
