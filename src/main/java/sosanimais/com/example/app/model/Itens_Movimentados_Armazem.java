package sosanimais.com.example.app.model;

import java.util.Date;

public class Itens_Movimentados_Armazem {

    private int idArm;
    private int idDoacao;
    private int idFunc;
    private int idCompra;
    private int qtde;
    private Date data;

    public Itens_Movimentados_Armazem(int idArm, int idDoacao, int idFunc, int idCompra, int qtde, Date data) {
        this.idArm = idArm;
        this.idDoacao = idDoacao;
        this.idFunc = idFunc;
        this.idCompra = idCompra;
        this.qtde = qtde;
        this.data = data;
    }

    public int getIdArm() {
        return idArm;
    }

    public void setIdArm(int idArm) {
        this.idArm = idArm;
    }

    public int getIdDoacao() {
        return idDoacao;
    }

    public void setIdDoacao(int idDoacao) {
        this.idDoacao = idDoacao;
    }

    public int getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(int idFunc) {
        this.idFunc = idFunc;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
