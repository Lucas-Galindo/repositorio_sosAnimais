package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.DAL.ServicoDAL;

import java.util.List;

public class Servico {

    private Long cod;
    private String nome;
    private String descricao;

    public Servico(Long cod,String nome,String descricao){
        this.cod = cod;
        this.nome = nome;
        this.descricao = descricao;
    }
    public Servico(){
        this(0L,"","");
    }

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    ServicoDAL repositorio = new ServicoDAL();
    public boolean save(Servico elemento){ return repositorio.save(elemento);}
    public boolean update(Servico elemento){ return repositorio.update(elemento);}
    public boolean delete(Servico elemento){ return repositorio.delete(elemento);}
    public List<Servico> getAll(String filtro){ return repositorio.get(filtro);}
    public Servico getServicoCod(Long cod){ return repositorio.get(cod);}
    public Servico getServicoNome(String nome){ return repositorio.findByNome(nome);}
}
