package sosanimais.com.example.app.model;

public class Endereco {
    private String rua;
    private int numero;
    private String cep;
    private String complemento;

    public Endereco(String rua,int numero,String cep,String complemento){
        this.rua=rua;
        this.numero=numero;
        this.cep=cep;
        this.complemento=complemento;
    }

    public Endereco(){

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
