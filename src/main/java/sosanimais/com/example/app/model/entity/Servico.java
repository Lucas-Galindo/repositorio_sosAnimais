package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.DAL.ServicoDAL;

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

}
