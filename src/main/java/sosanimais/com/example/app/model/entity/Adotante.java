package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.PessoaInformacao;

public class Adotante extends Pessoa {
    
    private int matricula;

    public Adotante(int id, PessoaInformacao pessoa, int matricula){
        super(id,pessoa);
        this.matricula = matricula;
    }

}
