package sosanimais.com.example.app.model.entity;

import java.sql.Date;

public class Acolhimento {
    private int id;
    private Date data;
    private int idFunc;

    public Acolhimento(int id, Date data, int func){
        this.id=id;
        this.data = data;
        this.idFunc=func;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(int idFunc) {
        this.idFunc = idFunc;
    }
}
