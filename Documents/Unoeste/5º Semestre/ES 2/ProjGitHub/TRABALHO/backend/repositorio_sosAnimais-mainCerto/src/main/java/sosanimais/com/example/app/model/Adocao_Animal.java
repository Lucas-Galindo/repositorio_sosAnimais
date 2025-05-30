package sosanimais.com.example.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Adocao_Animal{
    private Long adoCod;
    private Long funcCod;
    private Long aniCod;
    private int adoMat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date adoData;

    public Adocao_Animal(Long adoCod, Long funcCod, Long aniCod, int adoMat) {
        this.adoCod = adoCod;
        this.funcCod = funcCod;
        this.aniCod = aniCod;
        this.adoMat = adoMat;
    }

    public Adocao_Animal(Long adoCod, Long funcCod, Long aniCod, int adoMat, Date dataSaida) {
        this.adoCod = adoCod;
        this.funcCod = funcCod;
        this.aniCod = aniCod;
        this.adoMat = adoMat;
        this.adoData = dataSaida;

    }

    public Adocao_Animal(Long adoCod, Date dataSaida, Long aniCod, int adoMat) {
        this.adoCod = adoCod;
        this.adoData = dataSaida;
        this.adoMat = adoMat;
        this.aniCod = aniCod;
    }

    public Adocao_Animal() {
    }

    public Long getAdoCod() {return adoCod;}

    public void setAdoCod(Long adoCod) {
        this.adoCod = adoCod;
    }

    public Long getFuncCod() {
        return funcCod;
    }

    public void setFuncCod(Long funcCod) {
        this.funcCod = funcCod;
    }

    public Long getAniCod() {
        return aniCod;
    }

    public void setAniCod(Long aniCod) {
        this.aniCod = aniCod;
    }

    public int getAdoMat() {
        return adoMat;
    }

    public void setAdoMat(int adoMat) {
        this.adoMat = adoMat;
    }

    public Date getAdoData() {
        return adoData;
    }

    public void setAdoData(Date adoData) {
        this.adoData = adoData;
    }
}
