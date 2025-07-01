package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.DAL.TransferenciaDAL;
import sosanimais.com.example.app.model.Transfere_to_Baia;
import sosanimais.com.example.app.model.objetosAux.FiltrosTransferencia;

import java.util.Date;
import java.util.List;

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

    TransferenciaDAL repositorio = new TransferenciaDAL();

    public Transferencia salvarTransferencia(Transferencia entidade){ return repositorio.saveTransfere(entidade);}
    public Transferencia getId(Long mat){ return repositorio.get(mat);}
    public List<Transferencia> getAll(String filtro) {return repositorio.get(filtro);}
    public boolean deletar(Transferencia entidade){return repositorio.delete(entidade);}
    public boolean atualizar(Transferencia entidade){
        return repositorio.update(entidade);
    }


    public boolean salvarDados(Transfere_to_Baia elemento){ return repositorio.saveAssociativa(elemento);}
    public Transferencia getRegistroData(Date data){ return repositorio.findByDate(data);}
    public Transferencia getRegistroFunc(int mat){ return repositorio.findByMat(mat);}
    public List<Transferencia> pesquisaTransfere(Long id){
        return repositorio.searchTransferencia(id);
    }
    public Transferencia pesquisaDetalhesTransfere(FiltrosTransferencia filtro){
        return repositorio.searchDetailsTransfere(filtro);
    }


}
