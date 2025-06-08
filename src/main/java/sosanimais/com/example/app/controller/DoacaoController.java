package sosanimais.com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.DoacaoService;
import sosanimais.com.example.app.model.entity.Doacao;
import sosanimais.com.example.app.model.util.Erro; // Sua classe de Erro

import java.util.List; // Import para List

@RestController
@CrossOrigin("*") // Permite requisições de qualquer origem (ajuste para produção)
@RequestMapping("/apis/doacoes")
public class DoacaoController {

    private final DoacaoService doacaoService;

    @Autowired
    public DoacaoController(DoacaoService doacaoService) {
        this.doacaoService = doacaoService;
    }

    @PostMapping
    public ResponseEntity<Object> registrarNovaDoacao(@RequestBody Doacao doacao) {
        if (doacao == null) {
            return ResponseEntity.badRequest().body(new Erro("Objeto de doação não pode ser nulo."));
        }
        // Validações mais robustas podem ser adicionadas no DoacaoService
        Doacao doacaoSalva = doacaoService.registrarDoacao(doacao);

        if (doacaoSalva != null && doacaoSalva.getId() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(doacaoSalva);
        } else {
            // Se o serviço retornou null, pode ser um erro de validação ou de persistência
            // Idealmente, o serviço lançaria uma exceção específica ou retornaria um objeto de erro mais detalhado
            return ResponseEntity.badRequest().body(new Erro("Não foi possível registrar a doação. Verifique os dados ou ocorreu um erro interno."));
        }
    }

    // NOVO: Endpoint para listar todas as doações
    @GetMapping
    public ResponseEntity<List<Doacao>> listarTodasDoacoes() {
        List<Doacao> doacoes = doacaoService.listarTodasDoacoes(); // Você precisará criar este método no DoacaoService e DoacaoDAL
        if (doacoes == null) {
            // Isso pode indicar um erro ao buscar doações, não apenas uma lista vazia.
            // Considere retornar um status 500 ou um objeto de erro mais específico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        // Retorna a lista, que pode estar vazia (o que é um resultado OK)
        return ResponseEntity.ok(doacoes);
    }

    // NOVO: Endpoint para buscar uma doação específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarDoacaoPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(new Erro("ID da doação inválido."));
        }
        Doacao doacao = doacaoService.buscarDoacaoPorId(id); // Você precisará criar este método no DoacaoService e DoacaoDAL
        if (doacao != null) {
            return ResponseEntity.ok(doacao);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro("Doação com ID " + id + " não encontrada."));
        }
    }

    // NOVO E CORRIGIDO: Endpoint para deletar uma doação
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarDoacao(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(new Erro("ID da doação inválido."));
        }

        boolean deletado = doacaoService.deletarDoacao(id);

        if (deletado) {
            return ResponseEntity.noContent().build(); // 204 No Content - sucesso, sem corpo de resposta
        } else {
            // Isso pode acontecer se a doação não for encontrada ou se houver um erro ao deletar
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Erro("Não foi possível deletar a doação com ID: " + id + ". Pode não existir ou ocorreu um erro."));
        }
    }
}
