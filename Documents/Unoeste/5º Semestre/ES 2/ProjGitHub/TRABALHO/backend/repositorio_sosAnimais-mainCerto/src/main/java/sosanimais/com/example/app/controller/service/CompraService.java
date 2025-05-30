package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.Compra;
import sosanimais.com.example.app.model.DAL.CompraDAL;

import java.sql.SQLException;
import java.util.List;

@Service
public class CompraService {
    @Autowired
    private CompraDAL compraDAL;

    public Compra salvarCompra(Compra compra) {
        System.out.println("Iniciando salvamento de compra no service");
        boolean save = compraDAL.save(compra);
        if (save) {
            System.out.println("Compra salva com sucesso");
            return compra;
        }
        System.out.println("Erro ao salvar compra");
        return null;
    }

    public Compra atualizarCompra(Compra compra) {
        System.out.println("Iniciando atualização de compra no service");
        boolean update = compraDAL.update(compra);
        if (update) {
            System.out.println("Compra atualizada com sucesso");
            return compra;
        }
        System.out.println("Erro ao atualizar compra");
        return null;
    }

    public boolean deletarCompra(Compra compra) {
        System.out.println("=== SERVICE: Iniciando exclusão de compra ===");
        System.out.println("Dados da compra no service:");
        System.out.println("ID: " + compra.getCompraCod());
        System.out.println("Produto: " + compra.getProdutoNome());
        
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