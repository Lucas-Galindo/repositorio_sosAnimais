package sosanimais.com.example.app.model;

public class Transfere_to_Baia {
    private Long ttb;
    private Long transfId;
    private Long aniId;
    private Long baiaOrigem;
    private Long baiaDestino;


    public Transfere_to_Baia(Long ttb, Long transfId, Long aniId, Long baiaOrigem, Long baiaDestino) {
        this.ttb = ttb;
        this.transfId = transfId;
        this.aniId = aniId;
        this.baiaOrigem = baiaOrigem;
        this.baiaDestino = baiaDestino;
    }

    public Long getTtb() {
        return ttb;
    }

    public void setTtb(Long ttb) {
        this.ttb = ttb;
    }

    public Long getTransfId() {
        return transfId;
    }

    public void setTransfId(Long transfId) {
        this.transfId = transfId;
    }

    public Long getAniId() {
        return aniId;
    }

    public void setAniId(Long aniId) {
        this.aniId = aniId;
    }

    public Long getBaiaOrigem() {
        return baiaOrigem;
    }

    public void setBaiaOrigem(Long baiaOrigem) {
        this.baiaOrigem = baiaOrigem;
    }

    public Long getBaiaDestino() {
        return baiaDestino;
    }

    public void setBaiaDestino(Long baiaDestino) {
        this.baiaDestino = baiaDestino;
    }
}
