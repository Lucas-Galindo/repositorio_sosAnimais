package sosanimais.com.example.app.model;

import java.util.Date;

public class Adocao_Animal {
    private int idFunc;
    private int idAni;
    private int matAdotante;
    private Date dataSaida;

    public Adocao_Animal(int idFunc, int idAni, int matAdotante) {
        this.idFunc = idFunc;
        this.idAni = idAni;
        this.matAdotante = matAdotante;
    }

    public int getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(int idFunc) {
        this.idFunc = idFunc;
    }

    public int getIdAni() {
        return idAni;
    }

    public void setIdAni(int idAni) {
        this.idAni = idAni;
    }

    public int getMatAdotante() {
        return matAdotante;
    }

    public void setMatAdotante(int matAdotante) {
        this.matAdotante = matAdotante;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }
}
