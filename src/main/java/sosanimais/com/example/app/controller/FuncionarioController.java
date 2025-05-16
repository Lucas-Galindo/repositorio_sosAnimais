package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sosanimais.com.example.app.model.DAL.FuncionarioDAL;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

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

//    public FuncionarioController() {
//        this.funcionarioDAL = new FuncionarioDAL();
//    }
//
//    //Verifica o login do funcionário
//    public Funcionario validarLogin(String login, String senha) {
//        return funcionarioDAL.validarLogin(login, senha);
//    }
//
//    //Registra novo funcionario
//    public boolean registra(Funcionario funcionario) {
//        return funcionarioDAL.registrar(funcionario);
//    }
//
//    //Atualiza dados do funcionário existente
//    public boolean atualizar(Funcionario funcionario) {
//        return funcionarioDAL.atualizar(funcionario);
//    }
//
//    //Buscar funcionário por ID
//    public Funcionario buscarPorId(Long id) {
//        return funcionarioDAL.buscarPorId(id);
//    }
//
//    //Lista todos os funcionários
//    public List<Funcionario> listarTodos() {
//        return funcionarioDAL.listarTodos();
//    }




}
