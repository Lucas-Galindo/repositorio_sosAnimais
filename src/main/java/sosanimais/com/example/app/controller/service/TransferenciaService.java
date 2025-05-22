package sosanimais.com.example.app.controller.service;

import sosanimais.com.example.app.model.DAL.TransferenciaDAL;
import sosanimais.com.example.app.model.Transferencia;

import java.util.List;

public class TransferenciaService {
    TransferenciaDAL repositorio = new TransferenciaDAL();

    public boolean cadastro(Transferencia entidade){return repositorio.save(entidade);}
    public Transferencia getId(Long mat){return repositorio.get(mat);}
    public List<Transferencia> getAll(String filtro) {return repositorio.get(filtro);}
    public boolean deletar(Transferencia entidade){return repositorio.delete(entidade);}
    public boolean atualizar(Transferencia entidade){
        return repositorio.update(entidade);
    }
}
