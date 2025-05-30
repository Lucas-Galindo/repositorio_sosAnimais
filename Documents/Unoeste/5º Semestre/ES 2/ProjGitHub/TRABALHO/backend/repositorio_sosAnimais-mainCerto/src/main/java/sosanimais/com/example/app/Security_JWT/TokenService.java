package sosanimais.com.example.app.Security_JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.entity.Funcionario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private JWTProperties jwtProperties;
    private String chave;//private String chave="minha-chave-secreta";

    //Criando a variável de ambiente

    public TokenService(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct //Inicializando a chave secreta
    public void init() {
        this.chave = jwtProperties.getSecret();
    }

    //Gerando o token para validação do funcionário
    public String generateToken(Funcionario func){
        try{
            Algorithm algorithm = Algorithm.HMAC256(chave);
            String token = JWT.create()
                    .withSubject(func.getLogin())
                    .withExpiresAt(generateExpiration())
                    .sign(algorithm);
            return token;
        }catch(JWTCreationException e){
            throw new RuntimeException("Erro ao gerar o token", e);
        }
    }

    //Verificando se o token ainda esta válido com o funcionário logado
    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(chave);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            return "";
        }
    }

    //Tempo de uso do sistema até precisar logar novamente
    private Instant generateExpiration(){
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }
}
