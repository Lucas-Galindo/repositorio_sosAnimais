package sosanimais.com.example.app.model.entity;

public class Insumos {
    private int id;
    private int idCat;

    public Insumos(int id, int idCat) {
        this.id = id;
        this.idCat = idCat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }
}
