package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.PessoaInformacao;

public class Doador extends Pessoa {
    private int matricula;

    public Doador(int id, PessoaInformacao pessoa, int matricula){
        super(id,pessoa);
        this.matricula = matricula;
    }
}
