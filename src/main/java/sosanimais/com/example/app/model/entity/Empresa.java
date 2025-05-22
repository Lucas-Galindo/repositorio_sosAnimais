package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.Endereco;

public class Empresa {
    private int id;
    private int capacidade;
    private String cnpj;
    private String nome;
    private String NomeFantasia;
    private Endereco endereco;
    private String descricao;
    private String telefone;

    private String historia;

    public Empresa(int id, int capacidade, String cnpj, String nome, String nomeFantasia, String descricao, String telefone) {
    }


    public Empresa(String nome, String nomeFantasia, Endereco endereco, String descricao, String telefone, int id, int capacidade, String cnpj, String historia) {
        this.nome = nome;
        NomeFantasia = nomeFantasia;
        this.endereco = endereco;
        this.descricao = descricao;
        this.telefone = telefone;
        this.id = id;
        this.capacidade = capacidade;
        this.cnpj = cnpj;
        this.historia = historia;
    }

    public Empresa(int id, int capacidade, String cnpj, String nome, String nomeFantasia, String descricao) {
    }


    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getNomeFantasia() {
        return NomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        NomeFantasia = nomeFantasia;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(String rua, String cep, int numero) {
        this.endereco.setRua(rua);
        this.endereco.setCep(cep);
        this.endereco.setNumero(numero);
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}