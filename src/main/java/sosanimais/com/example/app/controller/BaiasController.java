package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.BaiasService;
import sosanimais.com.example.app.model.entity.Baias;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/apis/baias")
public class BaiasController {

    BaiasService baiaService = new BaiasService();
    

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Baias elemento) { // correto
        boolean aux = baiaService.cadastro(elemento);
        if (aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body(new Erro("Erro salvar Baias"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) { //correto
        boolean aux = baiaService.deletar(baiaService.getId(id));
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Baias"));

    }


    
    @PutMapping("/atualizar-objeto/{entidade}")
    public ResponseEntity<Object> atualizarElemento(@RequestBody Baias entidade) {
        boolean aux = baiaService.atualizarObjeto(entidade);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Baias"));
    }

    @PutMapping("/atualizar-ocupacao/{operacao}/{id}")
    public ResponseEntity<Object> incrementoQtde(@PathVariable String operacao, @PathVariable Long id) {
        char sinal = '-';
        if(operacao.equals("mais"))
            sinal = '+';

        boolean aux = baiaService.atualizarQuantidade(id,sinal);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar ocupacao Baias"));
    }


    @GetMapping("/lista") // correto
    public ResponseEntity<Object> getBaiaLista() { //coreto
        List<Baias> lista = baiaService.getAll("");
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Baias"));
    }

    @GetMapping("/lista/categoria/{filtro}")
    public ResponseEntity<Object> getBaiaLista(@PathVariable String filtro) {
        List<Baias> lista = baiaService.getAllByFilter(filtro);
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Baias"));
    }


    @GetMapping("/busca-baia-nome/{nome}") // correto
    public ResponseEntity<Object> getBaiaNome(@PathVariable String nome) { //coreto
        Baias aux = baiaService.getNomeBaia(nome);
        if (aux != null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Baias"));
    }

    @GetMapping("/busca-baia-id/{id}") // correto
    public ResponseEntity<Object> getBaiaId(@PathVariable Long id) { //coreto
        Baias aux = baiaService.getId(id);
        if (aux != null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Baias"));
    }

    @GetMapping("/busca-baia-categoria/{cat}/{id}") // correto
    public ResponseEntity<Object> getBaiaId(@PathVariable String cat,@PathVariable  Long id) { //coreto
        Baias aux = baiaService.getBaiaByIdNome(cat,id);
        if (aux != null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Baias"));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Object> getBaiasByCategoria(@PathVariable String categoria) {
        List<Baias> lista = baiaService.getAllByFilter(categoria);
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Baias por categoria"));
    }



}