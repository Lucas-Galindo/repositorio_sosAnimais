package sosanimais.com.example.app.model;

import java.util.Date;

public class Animal_Insumo {
    private Long aiId;
    private Long animalAniCod;
    private Long insumoIsuId;
    private Date aiDataExecucao;

    public Animal_Insumo() {}

    public Animal_Insumo(Long aiId, Long animalAniCod, Long insumoIsuId, Date aiDataExecucao) {
        this.aiId            = aiId;
        this.animalAniCod    = animalAniCod;
        this.insumoIsuId     = insumoIsuId;
        this.aiDataExecucao  = aiDataExecucao;
    }

    public Long getAiId() { return aiId; }
    public void setAiId(Long aiId) { this.aiId = aiId; }

    public Long getAnimalAniCod() { return animalAniCod; }
    public void setAnimalAniCod(Long animalAniCod) { this.animalAniCod = animalAniCod; }

    public Long getInsumoIsuId() { return insumoIsuId; }
    public void setInsumoIsuId(Long insumoIsuId) { this.insumoIsuId = insumoIsuId; }

    public Date getAiDataExecucao() { return aiDataExecucao; }
    public void setAiDataExecucao(Date aiDataExecucao) { this.aiDataExecucao = aiDataExecucao; }
}
