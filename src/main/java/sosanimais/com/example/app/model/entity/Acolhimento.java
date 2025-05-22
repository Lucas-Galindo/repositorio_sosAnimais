package sosanimais.com.example.app.model.entity;

import java.sql.Date;

public class Acolhimento {
    private Long id;
    private Date data;
    private Long idFunc;
    private Long idAnimal;

    public Acolhimento(Long id, Date data, Long func , Long animal) {
        this.id=id;
        this.data = data;
        this.idFunc=func;
        this.idAnimal=animal;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(Long idFunc) {
        this.idFunc = idFunc;
    }

    public Long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }
}
