package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.entity.Compra;
import sosanimais.com.example.app.model.DAL.CompraDAL;
import sosanimais.com.example.app.model.DAL.ProdutoDAL;
import sosanimais.com.example.app.model.entity.Produto;

import java.sql.SQLException;
import java.util.List;

@Service
public class CompraService {
    @Autowired
    private CompraDAL compraDAL;
    
    @Autowired
    private ProdutoService prodServ;

    private String lastError;

    public String getLastError() {
        return lastError;
    }

    private void setLastError(String error) {
        this.lastError = error;
    }

    public Compra salvarCompra(Compra compra) {
        setLastError(null);
        System.out.println("Iniciando salvamento de compra-----------------");
        System.out.println("Produto: " + compra.getProdutoNome());
        System.out.println("Quantidade: " + compra.getQuantidade());
        System.out.println("Valor: " + compra.getValorUnitario());
        System.out.println("Funcionário (mat): " + compra.getFuncCod());
        System.out.println("Data: " + compra.getDataCompra());

        if (compra.getFuncCod() == null) {
            setLastError("Matrícula do funcionário não pode ser nula");
            System.out.println("ERRO: " + getLastError());
            return null;
        }

        // Verificar se o produto existe
        List<Produto> produtos = prodServ.getByName(compra.getProdutoNome());
        if (produtos == null || produtos.isEmpty()) {
            setLastError("Produto não encontrado no cadastro");
            System.out.println("ERRO: " + getLastError());
            return null;
        }

        boolean save = compraDAL.save(compra);
        if (save) {
            System.out.println("Compra salva com sucesso");
            return compra;
        }
        setLastError("Erro ao salvar compra");
        System.out.println(getLastError());
        return null;
    }

    public Compra atualizarCompra(Compra compra) {
        boolean update = compraDAL.update(compra);
        if (update) {
            System.out.println("Compra atualizada com sucesso");
            return compra;
        }
        System.out.println("Erro ao atualizar compra");
        return null;
    }

    public boolean deletarCompra(Compra compra) {
        try {
            boolean result = compraDAL.delete(compra);
            System.out.println("Resultado da exclusão no service: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("ERRO no service durante exclusão: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Compra buscarPorId(Long id) {
        System.out.println("Buscando compra por ID: " + id);
        return compraDAL.get(id);
    }

    public List<Compra> buscarTodos() throws SQLException {
        System.out.println("Buscando todas as compras");
        return compraDAL.get("");
    }

    public List<Compra> buscarComFiltro(String filtro) throws SQLException {
        System.out.println("Buscando compras com filtro: " + filtro);
        return compraDAL.get(filtro);
    }
} 