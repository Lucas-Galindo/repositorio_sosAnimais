package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.InsumoDAL;
import sosanimais.com.example.app.model.entity.Insumos;

import java.util.List;

@Service
public class InsumoService {
    @Autowired
    private InsumoDAL insumoDAL;

    public Insumos salvar(Insumos insumo) {
        boolean ok = insumoDAL.save(insumo);
        return ok ? insumo : null;
    }

    public Insumos atualizar(Insumos insumo) {
        boolean ok = insumoDAL.update(insumo);
        return ok ? insumo : null;
    }

    public boolean deletar(Insumos insumo) {
        return insumoDAL.delete(insumo);
    }

    public Insumos buscarPorId(Long id) {
        return insumoDAL.get(id);
    }

    public List<Insumos> buscarTodos() {
        return insumoDAL.get("");
    }
    public List<Insumos> buscaByCategoriaIds(Long id1, Long id2){

        return insumoDAL.getByCategoriaIds(id1,id2);
    }

    public List<Insumos> buscarComFiltro(String filtro) {
        return insumoDAL.get(filtro);
    }
}
