package sosanimais.com.example.app.Security_JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sosanimais.com.example.app.controller.service.FuncionarioService;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private FuncionarioService funcServ;
    @Autowired
    private TokenBlacklist tokenBlacklist;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authHeader);
        var chave = this.recoverToken(request);

        if(chave != null && !tokenBlacklist.isBlacklisted(chave)){
            var login = tokenService.validateToken(chave);
            UserDetails func = funcServ.buscaLogin(login); //achou o usuario
            if (func != null) {
                var autenticacao = new UsernamePasswordAuthenticationToken(func, null, func.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticacao); //salvando o contexto da autenticação
            }
        }
        filterChain.doFilter(request, response);
    }

    public String recoverToken(HttpServletRequest request){
        var authHearder = request.getHeader("Authorization");
        if(authHearder == null || !authHearder.startsWith("Bearer "))
            return null;
        return authHearder.replace("Bearer ", "");
    }

}
