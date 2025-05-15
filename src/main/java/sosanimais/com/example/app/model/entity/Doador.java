package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.PessoaInformacao;

public class Doador extends Pessoa {
    private int matricula;

    public Doador(Long id, PessoaInformacao pessoa, int matricula){
        super(id,pessoa);
        this.matricula = matricula;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
}
