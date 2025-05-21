package sosanimais.com.example.app.model;

public class PessoaInformacao {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;


    public PessoaInformacao(PessoaInformacao pessoa) {
        this.nome = pessoa.nome;
        this.cpf = pessoa.cpf;
        this.telefone = pessoa.telefone;
        this.email = pessoa.email;

    }

    public PessoaInformacao(String nome, String cpf, String telefone, String email){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }
    public PessoaInformacao(){
        this("","","","");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}
