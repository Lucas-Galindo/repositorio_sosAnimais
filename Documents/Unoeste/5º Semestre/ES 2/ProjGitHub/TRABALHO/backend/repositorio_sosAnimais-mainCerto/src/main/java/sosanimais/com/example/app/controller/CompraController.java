package sosanimais.com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.CompraService;
import sosanimais.com.example.app.model.entity.Compra;
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
            if (filtro != null && !filtro.isEmpty()) {
                compras = compraService.buscarComFiltro(filtro);
            } else {
                compras = compraService.buscarTodos();
            }
        } catch (SQLException e) {
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
        Compra compra = compraService.buscarPorId(id);
        if (compra != null) {
            return ResponseEntity.ok(compra);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Não foi possível encontrar a compra de id: " + id));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> addCompra(@RequestBody Compra compra) {
        if (compra.getFuncCod() == null) {
            return ResponseEntity.badRequest().body(new Erro("A matrícula do funcionário é obrigatória"));
        }
        if (compra.getProdutoNome() == null || compra.getProdutoNome().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("O nome do produto é obrigatório"));
        }
        if (compra.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body(new Erro("A quantidade deve ser maior que zero"));
        }
        if (compra.getValorUnitario() <= 0) {
            return ResponseEntity.badRequest().body(new Erro("O valor unitário deve ser maior que zero"));
        }
        if (compra.getDataCompra() == null) {
            return ResponseEntity.badRequest().body(new Erro("A data da compra é obrigatória"));
        }

        Compra novaCompra = compraService.salvarCompra(compra);
        if (novaCompra != null) {
            return ResponseEntity.ok(novaCompra);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao salvar a compra"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCompra(@PathVariable Long id, @RequestBody Compra compra) {
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
            Compra compra = compraService.buscarPorId(id);
            if (compra == null) {
                return ResponseEntity.status(404).body(new Erro("Compra não encontrada com ID: " + id));
            }
            
            boolean status = compraService.deletarCompra(compra);
            
            if (status) {
                return ResponseEntity.ok().body("Compra excluída com sucesso!");
            } else {
                return ResponseEntity.status(409).body(new Erro("Não foi possível excluir a compra"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Erro("Erro ao processar a exclusão: " + e.getMessage()));
        }
    }
} 