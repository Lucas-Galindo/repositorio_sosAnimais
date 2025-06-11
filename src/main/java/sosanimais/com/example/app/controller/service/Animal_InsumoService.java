package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.Animal_Insumo;
import sosanimais.com.example.app.model.DAL.AnimalInsumoDAL;
import sosanimais.com.example.app.model.DAL.InsumoDAL;
import sosanimais.com.example.app.model.entity.Insumos;

import java.util.List;

@Service
public class Animal_InsumoService {

    @Autowired
    private AnimalInsumoDAL dal;
    @Autowired
    private InsumoDAL insumoDAL;

    public Animal_Insumo salvar(Animal_Insumo a) {
        if(dal.save(a)){
            Insumos insumos=insumoDAL.get(a.getInsumoIsuId());
            insumos.setQuantidade(insumos.getQuantidade()+-1);
            insumoDAL.update(insumos);
            return a;
        }

        return null;
    }

    public Animal_Insumo atualizar(Animal_Insumo a) {
        if(dal.update(a)==true)
            return a;
        return null;
    }

    public boolean deletar(Animal_Insumo a) {
        Insumos insumos=insumoDAL.get(a.getInsumoIsuId());
        insumos.setQuantidade(insumos.getQuantidade()+1);
        insumoDAL.update(insumos);
        return dal.delete(a);
    }

    public Animal_Insumo buscarPorId(Long id) {
        return dal.get(id);
    }

    public List<Animal_Insumo> buscarTodos() {
        return dal.get("");
    }

    public List<Animal_Insumo> buscarComFiltro(String filtro) {
        return dal.get(filtro);
    }
    public List<Animal_Insumo> buscarPorAnimal(String termo) {
        return dal.getByAnimal(termo);
    }
}
