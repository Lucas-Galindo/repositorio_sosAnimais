package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.ProdutoInformacao; // Usando sua classe existente
import java.time.LocalDate;

public class Doacao {
    private Long id; // Gerado pelo banco de dados
    private String nomeDoador;
    private String email;
    private String tipo; // "dinheiro", "remedios", "comida", "produtos"
    private String mensagem;
    private LocalDate dataDoacao;
    private Double valor; // Aplicável se tipo for "dinheiro"
    private ProdutoInformacao produto; // Detalhes do produto, se aplicável

    // Construtor vazio (necessário para o Jackson - conversor JSON)
    public Doacao() {
    }

    // Construtor completo (pode ser útil)
    public Doacao(String nomeDoador, String email, String tipo, String mensagem, LocalDate dataDoacao, Double valor, ProdutoInformacao produto) {
        this.nomeDoador = nomeDoador;
        this.email = email;
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.dataDoacao = dataDoacao;
        this.valor = valor;
        this.produto = produto;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataDoacao() {
        return dataDoacao;
    }

    public void setDataDoacao(LocalDate dataDoacao) {
        this.dataDoacao = dataDoacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public ProdutoInformacao getProduto() {
        return produto;
    }

    public void setProduto(ProdutoInformacao produto) {
        this.produto = produto;
    }
}
