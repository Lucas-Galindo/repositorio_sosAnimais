package sosanimais.com.example.app.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sosanimais.com.example.app.controller.services.FuncionarioService;
import sosanimais.com.example.app.model.entity.Funcionario;

@RestController("/apis/funcionario")
public class FuncionarioController {
    
    FuncionarioService funcService;

    @PostMapping
    public ResponseEntity<Object> cadastro(@PathVariable Funcionario elemento){
        boolean aux = funcService.save(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body("erro");
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        
    }


}
