package sosanimais.com.example.app.model.entity;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sosanimais.com.example.app.Security_JWT.FuncionarioRole;
import sosanimais.com.example.app.model.PessoaInformacao;

public class Funcionario extends Pessoa implements UserDetails {
    private int matricula;
    private String login;
    private String senha;
    private FuncionarioRole role;


    public Funcionario(Long id, PessoaInformacao pessoa, int matricula, String login, String senha, String role) {
        super(id, pessoa);
        this.matricula = matricula;
        this.login = login;
        this.senha = senha;
        this.role = FuncionarioRole.valueOf(role);
    }

    //public Funcionario(){
    //    this(0L,null,0,"","");
    //}

    //Construtores para Login
    public Funcionario(String login, String senha){
        this.login = login;
        this.senha = senha;
    }
    public Funcionario(String login, String senha, FuncionarioRole role){
        this.login = login;
        this.senha = senha;
        this.role = role;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getRole(){
        return role.name().toUpperCase();
    }

    public  void setRole(String role){
        this.role = FuncionarioRole.valueOf(role.toUpperCase());
    }

    //Métodos do SpringSecurity
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == FuncionarioRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return senha;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return login;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
