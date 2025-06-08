package sosanimais.com.example.app.model.objetosAux;

import org.springframework.web.bind.annotation.RequestParam;

public class FiltrosTransferencia {
    private String dataInicial;
    private String dataFinal;
    private int matFunc;
    private String nomeFunc;
    private String categoriaBaias;

    public FiltrosTransferencia(String dataInicial, String dataFinal, int matFunc, String nomeFunc, String categoriaBaias) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.matFunc = matFunc;
        this.nomeFunc = nomeFunc;
        this.categoriaBaias = categoriaBaias;
    }

    public FiltrosTransferencia(){}

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public int getMatFunc() {
        return matFunc;
    }

    public void setMatFunc(int matFunc) {
        this.matFunc = matFunc;
    }

    public String getNomeFunc() {
        return nomeFunc;
    }

    public void setNomeFunc(String nomeFunc) {
        this.nomeFunc = nomeFunc;
    }

    public String getCategoriaBaias() {
        return categoriaBaias;
    }

    public void setCategoriaBaias(String categoriaBaias) {
        this.categoriaBaias = categoriaBaias;
    }
}
