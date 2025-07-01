package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.DAL.DoadorDAL;
import sosanimais.com.example.app.model.PessoaInformacao;

import java.util.List;

public class Doador extends Pessoa {
    private int matricula;

    public Doador(Long id, PessoaInformacao pessoa, int matricula){
        super(id,pessoa);
        this.matricula = matricula;
    }

    public Doador(){
        this(0L,null,0);
    }


    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    DoadorDAL repositorio = new DoadorDAL();

    public boolean cadastro(Doador entidade){
        return repositorio.save(entidade);
    }
    public Doador getId(Long mat){
        return repositorio.get(mat);
    }
    public List<Doador> getAll(String filtro)  {
        return repositorio.get(filtro);
    }
    public boolean deletar(Doador entidade){
        return repositorio.delete(entidade);
    }
    public boolean atualizar(Doador entidade){
        return repositorio.update(entidade);
    }

    public Doador getPessoaId(Long id){ return repositorio.findByPessoaId(id);}
    public boolean deletePess(Long id){ return repositorio.deletePessoa(id);}


}
