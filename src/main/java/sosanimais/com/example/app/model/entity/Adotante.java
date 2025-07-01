package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.DAL.AdotanteDAL;
import sosanimais.com.example.app.model.PessoaInformacao;

import java.util.List;

public class Adotante extends Pessoa {
    
    private int matricula;

    public Adotante(Long id, PessoaInformacao pessoa, int matricula){
        super(id,pessoa);
        this.matricula = matricula;
    }


    public Adotante(){
        this(0L,null,0);
    }
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    AdotanteDAL repositorio = new AdotanteDAL();

    public boolean cadastro(Adotante entidade){
        return repositorio.save(entidade);
    }
    public Adotante getId(Long mat){
        return repositorio.get(mat);
    }
    public List<Adotante> getAll(String filtro)  {
        return repositorio.get(filtro);
    }
    public boolean deletar(Adotante entidade){
        return repositorio.delete(entidade);
    }
    public boolean atualizar(Adotante entidade){
        return repositorio.update(entidade);
    }

    public Adotante getPessoaId(Long id){ return repositorio.findByPessoaId(id);}
    public boolean deletePess(Long id){ return repositorio.deletePessoa(id);}



}
