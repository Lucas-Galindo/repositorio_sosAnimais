package sosanimais.com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.CompraService;
import sosanimais.com.example.app.model.Compra;
import sosanimais.com.example.app.model.util.Erro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<Object> getAllCompras(@RequestParam(required = false) String filtro) {
        List<Compra> compras = new ArrayList<>();
        try {
            System.out.println("Filtro recebido: " + filtro);
            if (filtro != null && !filtro.isEmpty()) {
                compras = compraService.buscarComFiltro(filtro);
            } else {
                compras = compraService.buscarTodos();
            }
            System.out.println("Número de compras encontradas: " + compras.size());
        } catch (SQLException e) {
            System.out.println("Erro ao buscar compras: " + e.getMessage());
            throw new RuntimeException(e);
        }
        if (!compras.isEmpty()) {
            return ResponseEntity.ok(compras);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Nenhuma compra encontrada!"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCompraById(@PathVariable Long id) {
        System.out.println("Buscando compra com ID: " + id);
        Compra compra = compraService.buscarPorId(id);
        if (compra != null) {
            return ResponseEntity.ok(compra);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Não foi possível encontrar a compra de id: " + id));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> addCompra(@RequestBody Compra compra) {
        System.out.println("Registrando nova compra");
        Compra novaCompra = compraService.salvarCompra(compra);
        if (novaCompra != null) {
            return ResponseEntity.ok(novaCompra);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao salvar a compra"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCompra(@PathVariable Long id, @RequestBody Compra compra) {
        System.out.println("Atualizando compra com ID: " + id);
        compra.setCompraCod(id);
        Compra compraAtualizada = compraService.atualizarCompra(compra);
        if (compraAtualizada != null) {
            return ResponseEntity.ok(compraAtualizada);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar a compra"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompra(@PathVariable Long id) {
        try {
            System.out.println("=== INÍCIO DO PROCESSO DE EXCLUSÃO DE COMPRA ===");
            System.out.println("ID da compra a ser excluída: " + id);
            
            Compra compra = compraService.buscarPorId(id);
            System.out.println("Compra encontrada: " + (compra != null));
            
            if (compra == null) {
                System.out.println("Compra não encontrada no banco de dados!");
                return ResponseEntity.badRequest().body(new Erro("Compra não encontrada!"));
            }
            
            System.out.println("Dados da compra:");
            System.out.println("ID: " + compra.getCompraCod());
            System.out.println("Produto: " + compra.getProdutoNome());
            System.out.println("Funcionário: " + compra.getFuncCod());
            
            boolean status = compraService.deletarCompra(compra);
            System.out.println("Status da exclusão: " + status);
            
            if (status) {
                System.out.println("Compra excluída com sucesso!");
                return ResponseEntity.ok("Compra excluída com sucesso!");
            } else {
                System.out.println("Falha ao excluir compra!");
                return ResponseEntity.badRequest().body(new Erro("Erro ao excluir a compra!"));
            }
        } catch (Exception e) {
            System.out.println("ERRO durante a exclusão: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro("Erro ao processar a exclusão: " + e.getMessage()));
        }
    }
} 