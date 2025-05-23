package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.entity.Doacao;
import sosanimais.com.example.app.controller.service.DoacaoService;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final DoacaoService doacaoService = new DoacaoService();

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> buscarPorNomeProduto(@PathVariable String nome) {
        List<Doacao> doacoes = doacaoService.listarTodas();

        List<Doacao> filtradas = doacoes.stream()
                .filter(d -> d.getProduto() != null && d.getProduto().getNome() != null)
                .filter(d -> d.getProduto().getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());

        if (filtradas.isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("Nenhum produto com esse nome foi encontrado"));
        }

        return ResponseEntity.ok(filtradas);
    }

    @GetMapping("/validade/{validade}")
    public ResponseEntity<Object> buscarPorValidadeProduto(@PathVariable String validade) {
        List<Doacao> doacoes = doacaoService.listarTodas();

        List<Doacao> filtradas = doacoes.stream()
                .filter(d -> d.getProduto() != null && d.getProduto().getValidade() != null)
                .filter(d -> d.getProduto().getValidade().equalsIgnoreCase(validade))
                .collect(Collectors.toList());

        if (filtradas.isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("Nenhum produto com essa validade foi encontrado"));
        }

        return ResponseEntity.ok(filtradas);
    }
}
