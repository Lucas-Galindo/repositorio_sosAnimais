package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.PessoaInformacao;

public class Funcionario extends Pessoa {
    private int matricula;
    private String login;
    private String senha;


    public Funcionario(Long id, PessoaInformacao pessoa, int matricula, String login, String senha) {
        super(id, pessoa);
        this.matricula = matricula;
        this.login = login;
        this.senha = senha;
    }


    public Funcionario(){
        this(0L,null,0,"","");
    }

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
