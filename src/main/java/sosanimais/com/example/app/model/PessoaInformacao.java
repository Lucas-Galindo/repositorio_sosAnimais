package sosanimais.com.example.app.model;

public class PessoaInformacao {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
<<<<<<< HEAD
    //private Endereco adress;

    public PessoaInformacao(String nome,String cpf,String telefone,String email){
=======
    private Endereco adress;

    public PessoaInformacao(String nome,String cpf,String telefone,String email,String rua, int numero, String cep, String complemento){
>>>>>>> c79675591135cd43030da201dac6a938754b568b
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;    
<<<<<<< HEAD
        //this.adress = new Endereco(rua, numero, cep, complemento);
    }

    public PessoaInformacao(){

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

//    public Endereco getAdress() {
//        return adress;
//    }

//    public void setAdress(Endereco adress) {
//        this.adress = adress;
//    }
=======
        this.adress = new Endereco(rua, numero, cep, complemento);
    }
>>>>>>> c79675591135cd43030da201dac6a938754b568b
}
