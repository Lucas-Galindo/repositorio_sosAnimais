package sosanimais.com.example.app.model;

public class Endereco {
    private String rua;
    private int numero; // Mantido como int conforme a sua classe
    private String cep;
    private String complemento;
    // Campos bairro, cidade, estado não existem aqui, serão ignorados pelo Jackson se vierem no JSON
    // e não serão usados pelo DAL para construir a string emp_endereco.

    public Endereco(String rua, int numero, String cep, String complemento) {
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
        this.complemento = complemento;
    }

    public Endereco() {
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
