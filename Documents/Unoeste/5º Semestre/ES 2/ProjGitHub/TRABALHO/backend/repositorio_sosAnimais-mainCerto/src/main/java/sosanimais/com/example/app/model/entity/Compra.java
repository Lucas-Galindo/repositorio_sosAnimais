package sosanimais.com.example.app.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Compra {
    private Long comp_id;
    private String produto_prod_nome;
    private int comp_qtd;
    private double comp_valorTotal;
    private Long funcionario_func_cod;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
    private Date comp_data;

    public Compra() {
    }

    public Compra(Long comp_id, String produto_prod_nome, int comp_qtd, double comp_valorTotal, Long funcionario_func_cod, Date comp_data) {
        this.comp_id = comp_id;
        this.produto_prod_nome = produto_prod_nome;
        this.comp_qtd = comp_qtd;
        this.comp_valorTotal = comp_valorTotal;
        this.funcionario_func_cod = funcionario_func_cod;
        this.comp_data = comp_data;
    }

    public Long getCompraCod() {
        return comp_id;
    }

    public void setCompraCod(Long comp_id) {
        this.comp_id = comp_id;
    }

    public String getProdutoNome() {
        return produto_prod_nome;
    }

    public void setProdutoNome(String produto_prod_nome) {
        this.produto_prod_nome = produto_prod_nome;
    }

    public int getQuantidade() {
        return comp_qtd;
    }

    public void setQuantidade(int comp_qtd) {
        this.comp_qtd = comp_qtd;
    }

    public double getValorUnitario() {
        return comp_valorTotal;
    }

    public void setValorUnitario(double comp_valorTotal) {
        this.comp_valorTotal = comp_valorTotal;
    }

    public Long getFuncCod() {
        return funcionario_func_cod;
    }

    public void setFuncCod(Long funcionario_func_cod) {
        this.funcionario_func_cod = funcionario_func_cod;
    }

    public Date getDataCompra() {
        return comp_data;
    }

    public void setDataCompra(Date comp_data) {
        this.comp_data = comp_data;
    }
} 