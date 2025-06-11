package sosanimais.com.example.app.model;

import java.util.Date;

public class Transferencia {
    private Long id;
    private Date data;
    private int matFunc;


    public Transferencia(Long id, Date data, int matFunc) {
        this.id = id;
        this.data = data;
        this.matFunc = matFunc;

    }
    public Transferencia(){
        this(0L,null,0);
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

    public int getMatFunc() {
        return matFunc;
    }

    public void setMatFunc(int matFunc) {
        this.matFunc = matFunc;
    }


}
