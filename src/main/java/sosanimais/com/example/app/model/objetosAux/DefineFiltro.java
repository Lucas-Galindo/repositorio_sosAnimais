package sosanimais.com.example.app.model.objetosAux;

public class DefineFiltro {

    private String filtro;

    public DefineFiltro(String filtro){
        this.filtro = TipoFiltro(filtro);
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String TipoFiltro(String filtro){
        String opcao = "";

        if(filtro.equals("ascendente"))
            opcao = "ASC";
        if(filtro.equals("descendente"))
            opcao = "DESC";

        return opcao;
    }


}
