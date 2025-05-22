package sosanimais.com.example.app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sosanimais.com.example.app.controller.service.DoadorService;
import sosanimais.com.example.app.model.entity.Adotante;
import sosanimais.com.example.app.model.entity.Doador;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/doador")
public class DoadorController {

    DoadorService doadorService = new DoadorService();

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Doador elemento){ // correto
        boolean aux = doadorService.cadastro(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body( new Erro("Erro salvar Doador"));
    }

    @GetMapping("/{mat}") // correto
    public ResponseEntity<Object> getDoadorId(@PathVariable Long mat){
        Doador aux = doadorService.getId(mat);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Doador"));
    }

    @GetMapping("/lista") // correto
    public ResponseEntity<Object> getDoadorLista() { //coreto
        List<Doador> lista = doadorService.getAll("");
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Doador"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getDoadorLista(@PathVariable String filtro) {
        List<Doador> lista = doadorService.getAll(filtro);
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Doador"));
    }


    @DeleteMapping("/exclusao-mat/{mat}")
    public ResponseEntity<Object> deletar(@PathVariable Long mat) { //correto
        boolean aux = doadorService.deletar(doadorService.getId(mat));
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Doador"));

    }

    @DeleteMapping("/exclusao-pessoa/{id}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable Long id) { //correto
        boolean aux = doadorService.deletePess(id);
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Doador"));

    }

    //Acho que aqui nao faz sentido atualização, nao tem porque sendo que so tem matricula
    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Doador entidade){
        boolean aux = doadorService.atualizar(entidade);
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Doador"));
    }


    // Usado para verificar se na tabela existe o id da pessoa, isso é usado em bucas e relatorios
    @GetMapping("/busca-pessoa/{idPessoa}")
    public ResponseEntity<Object> getPessoaId(@PathVariable Long idPessoa){
        Doador aux = doadorService.getPessoaId(idPessoa);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Não é doador"));
    }
}
