package sosanimais.com.example.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Compra {
    private Long compraCod;
    private String produtoNome;
    private int quantidade;
    private double valorUnitario;
    private Long funcCod;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
    private Date dataCompra;

    public Compra() {
    }

    public Compra(Long compraCod, String produtoNome, int quantidade, double valorUnitario, Long funcCod, Date dataCompra) {
        this.compraCod = compraCod;
        this.produtoNome = produtoNome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.funcCod = funcCod;
        this.dataCompra = dataCompra;
    }

    public Long getCompraCod() {
        return compraCod;
    }

    public void setCompraCod(Long compraCod) {
        this.compraCod = compraCod;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Long getFuncCod() {
        return funcCod;
    }

    public void setFuncCod(Long funcCod) {
        this.funcCod = funcCod;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }
} 