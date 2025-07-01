package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.DAL.FuncionarioDAL;
import sosanimais.com.example.app.model.PessoaInformacao;

import java.util.List;

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

    FuncionarioDAL repositorio = new FuncionarioDAL();

    public boolean cadastro(Funcionario entidade){return repositorio.save(entidade);}
    public Funcionario getId(Long mat){return repositorio.get(mat);}
    public List<Funcionario> getAll(String filtro) {return repositorio.get(filtro);}
    public boolean deletar(Funcionario entidade){
        return repositorio.delete(entidade);
    }
    public boolean atualizar(Funcionario entidade){
        return repositorio.update(entidade);
    }

    public Funcionario getPessoaId(Long id){ return repositorio.findByPessoaId(id);}
    public boolean deletarPess(Long id){ return repositorio.deletePessoa(id);}

}
