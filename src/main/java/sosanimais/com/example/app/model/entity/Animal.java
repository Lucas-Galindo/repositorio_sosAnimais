package sosanimais.com.example.app.model.entity;

<<<<<<< HEAD
import sosanimais.com.example.app.model.AnimalInformacao;
=======
import sosanimais.com.example.app.model.Animal_Informacao;
>>>>>>> c79675591135cd43030da201dac6a938754b568b

public class Animal {

    private int id;
    private int idBaia;
    private int idAcolhimento;
<<<<<<< HEAD
    private AnimalInformacao informacao;

    public Animal(int id, int idBaia, int idAcolhimento, AnimalInformacao informacao) {
=======
    private Animal_Informacao informacao;

    public Animal(int id, int idBaia, int idAcolhimento, Animal_Informacao informacao) {
>>>>>>> c79675591135cd43030da201dac6a938754b568b
        this.id = id;
        this.idBaia = idBaia;
        this.idAcolhimento = idAcolhimento;
        this.informacao = informacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBaia() {
        return idBaia;
    }

    public void setIdBaia(int idBaia) {
        this.idBaia = idBaia;
    }

    public int getIdAcolhimento() {
        return idAcolhimento;
    }

    public void setIdAcolhimento(int idAcolhimento) {
        this.idAcolhimento = idAcolhimento;
    }

<<<<<<< HEAD
    public AnimalInformacao getInformacao() {
        return informacao;
    }

    public void setInformacao(AnimalInformacao informacao) {
=======
    public Animal_Informacao getInformacao() {
        return informacao;
    }

    public void setInformacao(Animal_Informacao informacao) {
>>>>>>> c79675591135cd43030da201dac6a938754b568b
        this.informacao = informacao;
    }
}
