package sosanimais.com.example.app.Security_JWT.dto;

import sosanimais.com.example.app.Security_JWT.FuncionarioRole;
import sosanimais.com.example.app.model.PessoaInformacao;

public record FuncionarioNovoDTO(PessoaInformacao pessoa, String login, String senha, String role) {
}
