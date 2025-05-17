package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sosanimais.com.example.app.controller.service.EmpresaService;

import sosanimais.com.example.app.model.InfoEmpresa;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value=("/apis/Empresa"))
public class EmpresaController {

    EmpresaService EmpService = new EmpresaService();

    @PostMapping
    public ResponseEntity<Object> cadastro(@PathVariable  InfoEmpresa elemento){
        boolean aux = EmpService.cadastro(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body( new Erro("Erro salvar empresa"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmpId(@PathVariable Long id){
        int aux = EmpService.getId(id);
        if(aux<0)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar empresa"));
    }

    @GetMapping
    public ResponseEntity<Object> getEmpLista(){
        List<InfoEmpresa>lista = EmpService.getAll("");
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{filtro}")
    public ResponseEntity<Object> getEmpLista(@PathVariable String filtro){
        List<InfoEmpresa> lista = EmpService.getAll(filtro);
        return ResponseEntity.ok(lista);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id){
        boolean aux = EmpService.deletar(EmpService.getId(id));
        if(aux==true)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar empresa"));
    }


    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Funcionario entidade){
        boolean aux = EmpService.atualizar(entidade);
        if(aux == true)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar empresa"));
    }





}
