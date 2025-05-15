package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;
import sosanimais.com.example.app.model.DAL.FuncionarioDAL;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.util.Erro;

@RestController("/apis/funcionario")
public class FuncionarioController {
    
    FuncionarioDAL funcService;

    @PostMapping
    public ResponseEntity<Object> cadastro(@PathVariable Funcionario elemento){
        boolean aux = funcService.save(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body( new Erro("Erro salvar funcionario"));
    }



}
