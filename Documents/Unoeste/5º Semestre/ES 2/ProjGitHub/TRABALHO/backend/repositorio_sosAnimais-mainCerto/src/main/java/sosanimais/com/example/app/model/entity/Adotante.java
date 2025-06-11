package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.PessoaInformacao;

public class Adotante extends Pessoa {
    
    private int matricula;
    private Long adocaoAnimalAdotaCod;

    public Adotante(Long id, PessoaInformacao pessoa, int matricula){
        super(id,pessoa);
        this.matricula = matricula;
        this.adocaoAnimalAdotaCod = null;
    }

    public Adotante(Long id, PessoaInformacao pessoa, int matricula, Long adocaoAnimalAdotaCod){
        super(id,pessoa);
        this.matricula = matricula;
        this.adocaoAnimalAdotaCod = adocaoAnimalAdotaCod;
    }

    public Adotante(){
        this(0L,null,0);
    }

    public int getMatricula() {
        return matricula;
    }
    
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Long getAdocaoAnimalAdotaCod() {
        return adocaoAnimalAdotaCod;
    }

    public void setAdocaoAnimalAdotaCod(Long adocaoAnimalAdotaCod) {
        this.adocaoAnimalAdotaCod = adocaoAnimalAdotaCod;
    }
}
