package sosanimais.com.example.app.model;

import java.util.Date;

public class TransferirBaia {
    private Long id;
    private Date data;
    private int matFunc;
    private Long idAnimal;

    public TransferirBaia(Long id, Date data, int matFunc, Long idAnimal) {
        this.id = id;
        this.data = data;
        this.matFunc = matFunc;
        this.idAnimal = idAnimal;
    }
    public TransferirBaia(){
        this(0L,null,0,0L);
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

    public Long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }
}
