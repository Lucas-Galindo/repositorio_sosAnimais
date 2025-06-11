package sosanimais.com.example.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.ProdutoService;
import sosanimais.com.example.app.model.entity.Produto;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService = new ProdutoService();

    @PostMapping
    public ResponseEntity<Object> cadastrarProduto(@RequestBody Produto produto) {
        if (produto == null || produto.getProduto() == null) {
            return ResponseEntity.badRequest().body(new Erro("Dados do produto inválidos."));
        }
        boolean cadastrou = produtoService.cadastrarProduto(produto);
        if (cadastrou) {
            return ResponseEntity.status(HttpStatus.CREATED).body(produto);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao cadastrar produto. Verifique os dados fornecidos."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarProdutoPorId(@PathVariable int id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro("Produto não encontrado com o ID: " + id));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodosProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable int id) {
        Produto existente = produtoService.buscarProdutoPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro("Produto não encontrado com ID: " + id + " para exclusão."));
        }
        boolean deletou = produtoService.deletarProduto(id);
        if (deletou) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Erro("Erro ao deletar produto com ID: " + id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> buscarPorNomeProduto(@PathVariable String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("O nome para busca não pode ser vazio."));
        }
        List<Produto> filtrados = produtoService.buscarProdutosPorNome(nome);

        if (filtrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro("Nenhum produto com o nome similar a '" + nome + "' foi encontrado."));
        }
        return ResponseEntity.ok(filtrados);
    }

    @GetMapping("/validade/{validade}")
    public ResponseEntity<Object> buscarPorValidadeProduto(@PathVariable String validade) {
         if (validade == null || validade.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("A validade para busca não pode ser vazia."));
        }
        List<Produto> filtrados = produtoService.buscarProdutosPorValidade(validade);

        if (filtrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro("Nenhum produto com a validade '" + validade + "' foi encontrado."));
        }
        return ResponseEntity.ok(filtrados);
    }
}