package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.DAL.BaiasDAL;

import java.util.List;

public class Baias {

    private Long id;
    private int quantidadeAnimais;
    private String nome;
    private String categoria;

    public Baias(){
        this(0L,0,"","");
    }
    public Baias(Long id, int quantidadeAnimais, String nome, String categoria) {
        this.id = id;
        this.quantidadeAnimais = quantidadeAnimais;
        this.nome = nome;
       // this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidadeAnimais() {
        return quantidadeAnimais;
    }

    public void setQuantidadeAnimais(int quantidadeAnimais) {
        this.quantidadeAnimais = quantidadeAnimais;
    }

    private BaiasDAL repositorio = new BaiasDAL();

    // CRUD BASICO
    public boolean cadastro(Baias entidade){return repositorio.save(entidade);}
    public Baias getId(Long id){return repositorio.get(id);}
    public List<Baias> getAll(String filtro){return repositorio.get(filtro);}
    public boolean deletar(Baias entidade){return repositorio.delete(entidade);}
    public boolean atualizarObjeto(Baias entidade){return repositorio.update(entidade);}


    public boolean atualizarQuantidade(Long id, char sinal){return repositorio.updateQtde(id,sinal);}
    public List<Baias> getAllByFilter(String filtro) {return repositorio.getAllByBaia(filtro);}
    public Baias getNomeBaia(String nome) {return repositorio.findByNome(nome);}
    public Baias getBaiaByIdNome(String categoria, Long id) {return repositorio.findBaiaByIdNome(categoria, id);}


}
