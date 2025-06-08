package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.Endereco;

public class Empresa {

    private int id;
    private int capacidade;
    private String cnpj;
    private String nome;
    private String nomeFantasia;
    private Endereco endereco;
    private String descricao;
    private String telefone;
    private String historia; // Mantido para o formulário, mas não persistido no DB pelo DAL atual

    // Construtor vazio (OBRIGATÓRIO para Jackson/JPA)
    public Empresa() {
    }

    // Construtor usado pelo EmpresaDAL.montarEmpresa (sem historia, pois não vem do DB)
    public Empresa(int id, int capacidade, String cnpj, String nome, String nomeFantasia, Endereco endereco, String descricao, String telefone) {
        this.id = id;
        this.capacidade = capacidade;
        this.cnpj = cnpj;
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.endereco = endereco;
        this.descricao = descricao;
        this.telefone = telefone;
        // this.historia = null; // Não vem do DB nesta versão do DAL
    }

    // Construtor completo que pode ser usado ao receber dados do formulário (inclui historia)
    public Empresa(int id, int capacidade, String cnpj, String nome, String nomeFantasia, Endereco endereco, String descricao, String telefone, String historia) {
        this.id = id;
        this.capacidade = capacidade;
        this.cnpj = cnpj;
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.endereco = endereco;
        this.descricao = descricao;
        this.telefone = telefone;
        this.historia = historia;
    }


    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
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

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }
}
