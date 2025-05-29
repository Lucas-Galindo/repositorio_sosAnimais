package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.CategoriaDAL;
import sosanimais.com.example.app.model.entity.Categoria;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaDAL dal;

    public Categoria salvar(Categoria c) {
        return dal.save(c) ? c : null;
    }

    public Categoria atualizar(Categoria c) {
        return dal.update(c) ? c : null;
    }

    public boolean deletar(Categoria c) {
        return dal.delete(c);
    }

    public Categoria buscarPorId(Long id) {
        return dal.get(id);
    }

    public List<Categoria> buscarTodos() {
        return dal.get("");
    }

    public List<Categoria> buscarComFiltro(String filtro) {
        return dal.get(filtro);
    }
}