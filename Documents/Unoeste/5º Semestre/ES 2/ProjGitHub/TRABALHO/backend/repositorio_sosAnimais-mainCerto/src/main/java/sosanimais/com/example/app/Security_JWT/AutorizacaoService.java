package sosanimais.com.example.app.Security_JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.controller.service.FuncionarioService;

@Service
public class AutorizacaoService implements UserDetailsService {

    @Autowired
    FuncionarioService funcserv;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return funcserv.buscaLogin(login);
    }
}
