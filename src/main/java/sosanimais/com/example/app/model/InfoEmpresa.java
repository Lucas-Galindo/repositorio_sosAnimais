package sosanimais.com.example.app.model;

public class InfoEmpresa {
    private Endereco dados;
    private String Telefone;
    private String Descr;
    private int id;


    public InfoEmpresa(){
    }

    public InfoEmpresa(int id,Endereco end, String tel, String descr) {
        this.id=id;
        this.dados = end;
        this.Telefone = tel;
        this.Descr = descr;
    }

    public Endereco getEndereco() {
        return this.dados;
    }

    public int getId() {
        return id;
    }

    public Endereco getDados() {
        return dados;
    }

    public String getTelefone() {
        return Telefone;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDados(Endereco dados) {
        this.dados = dados;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }
}