package sosanimais.com.example.app.model;

import java.util.Date;

public class Func_Service {

    private int idServ;
    private int idFunc;
    private Date dataExecucao;

    public Func_Service(int idServ, int idFunc, Date dataExecucao) {
        this.idServ = idServ;
        this.idFunc = idFunc;
        this.dataExecucao = dataExecucao;
    }

    public int getIdServ() {
        return idServ;
    }

    public void setIdServ(int idServ) {
        this.idServ = idServ;
    }

    public int getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(int idFunc) {
        this.idFunc = idFunc;
    }

    public Date getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(Date dataExecucao) {
        this.dataExecucao = dataExecucao;
    }
}
