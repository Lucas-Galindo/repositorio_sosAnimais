package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.entity.Servico;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("apis/servico")
public class ServicoController {

    Servico servicoService = new Servico();

    //CRUD

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Servico elemento){
        boolean aux = servicoService.save(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body(new Erro("Erro ao cadastrar Servico"));
    }

    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Servico elemento){
        boolean aux = servicoService.update(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body(new Erro("Nao foi possivel atualizar"));
    }

    @DeleteMapping("/{cod}")
    public ResponseEntity<Object> deletar(@PathVariable Long cod){
        boolean aux = servicoService.delete(servicoService.getServicoCod(cod));
        if(aux)
            return ResponseEntity.ok(servicoService.getServicoCod(cod));
        return ResponseEntity.badRequest().body(new Erro("Nao foi possivel deletar elemento"));
    }


    @GetMapping("/busca-cod/{cod}")
    public ResponseEntity<Object> getServicoCod(@PathVariable Long cod){
        Servico aux = servicoService.getServicoCod(cod);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Nao foi possivel encontrar servico"));
    }

    @GetMapping("/lista")
    public ResponseEntity<Object> getServicoLista(){
        List<Servico> lista = servicoService.getAll("");
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Nao foi possivel retornar a lista de servico"));
    }

}
