package sosanimais.com.example.app.Security_JWT;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.Security_JWT.dto.FuncionarioDTO;
import sosanimais.com.example.app.Security_JWT.dto.LoginDTO;

@RestController
@RequestMapping("/auth")
public class AutorizacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenBlacklist tokenBlacklist;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody FuncionarioDTO func){
        var senha = new UsernamePasswordAuthenticationToken(func.login(), func.senha());
        var auth = this.authenticationManager.authenticate(senha);

        var token = tokenService.generateToken((Funcionario)auth.getPrincipal());

        return ResponseEntity.ok(new LoginDTO(token));
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
