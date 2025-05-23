package sosanimais.com.example.app.Security_JWT;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sosanimais.com.example.app.controller.service.FuncionarioService;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.Security_JWT.dto.FuncionarioDTO;
import sosanimais.com.example.app.Security_JWT.dto.FuncionarioNovoDTO;
import sosanimais.com.example.app.Security_JWT.dto.LoginDTO;

@RestController
@RequestMapping("/auth")
public class AutorizacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private FuncionarioService repos;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenBlacklist tokenBlacklist;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody FuncionarioDTO func){
        var senha = new UsernamePasswordAuthenticationToken(func.login(), func.senha());
        var auth = this.authenticationManager.authenticate(senha);

        var token = tokenService.generateToken((Funcionario)auth.getPrincipal());

        return ResponseEntity.ok(new LoginDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody FuncionarioNovoDTO func){
        String sql = "";
        if(this.repos.buscaLogin(func.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPAssoword = new BCryptPasswordEncoder().encode(func.senha());
        Funcionario funcNew = new Funcionario(func.login(), encryptedPAssoword, FuncionarioRole.valueOf(func.role().toUpperCase()));
        sql = """
                INSERT INTO funcionario(usu_id, func_login, func_senha, func_role) VALUES ('15','#2','#4', '#5');
                """;

        //sql = sql.replace("#1", 15);
        sql = sql.replace("#2", funcNew.getLogin());
        sql = sql.replace("#4", funcNew.getSenha());
        sql = sql.replace("#5", funcNew.getRole());
        //sql = sql.replace("#3", "" + entidade.getMatricula(());
        //sql = sql.replace("#3", "admin");

        boolean sucesso = SingletonDB.getConexao().manipular(sql);
        if (sucesso) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            tokenBlacklist.blacklist(token);
        }
        return ResponseEntity.ok("Logout realizado com sucesso");
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleException(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

}
