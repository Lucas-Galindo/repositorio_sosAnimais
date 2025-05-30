package sosanimais.com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.AdotanteService;
import sosanimais.com.example.app.controller.service.AnimalService;
import sosanimais.com.example.app.controller.service.FuncionarioService;
import sosanimais.com.example.app.model.entity.Adotante;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/adotante")
public class AdotanteController {

    private AdotanteService adoService;

    @Autowired // Injeção de dependência via construtor
    public AdotanteController(AdotanteService adoService) {
        this.adoService = adoService;
    }

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Adotante elemento){
        boolean aux = adoService.cadastro(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar Adotante"));
    }

    @GetMapping("/{mat}")
    public ResponseEntity<Object> getAdoId(@PathVariable Long mat){
        Adotante aux = adoService.getId(mat);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao encontrar Adotante"));
    }

    @GetMapping("/lista")
    public ResponseEntity<Object> getAdoLista() {
        List<Adotante> lista = adoService.getAll("");
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Adotantes"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getAdoLista(@PathVariable String filtro) {
        List<Adotante> lista = adoService.getAll(filtro);
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Adotantes"));
    }

    @DeleteMapping("/{mat}")
    public ResponseEntity<Object> deletar(@PathVariable Long mat) {
        boolean aux = adoService.deletar(adoService.getId(mat));
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Adotante"));
    }

    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Adotante entidade){
        boolean aux = adoService.atualizar(entidade);
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Adotante"));
    }

    @PutMapping("/{matricula}/adocao/{adocaoCod}")
    public ResponseEntity<Object> atualizarAdocao(@PathVariable Long matricula, @PathVariable Long adocaoCod) {
        boolean success = adoService.atualizarAdocao(matricula, adocaoCod);
        if (success) {
            return ResponseEntity.ok().body("Adoção atualizada com sucesso");
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar adoção do adotante"));
    }

    @DeleteMapping("/{matricula}/adocao")
    public ResponseEntity<Object> removerAdocao(@PathVariable Long matricula) {
        boolean success = adoService.removerAdocao(matricula);
        if (success) {
            return ResponseEntity.ok().body("Adoção removida com sucesso");
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao remover adoção do adotante"));
    }
}
