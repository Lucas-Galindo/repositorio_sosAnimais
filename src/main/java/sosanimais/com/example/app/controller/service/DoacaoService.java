package sosanimais.com.example.app.controller.service;

import sosanimais.com.example.app.model.DAL.DoacaoDAL;
import sosanimais.com.example.app.model.entity.Doacao;

import java.util.List;

public class DoacaoService {

    private final DoacaoDAL repositorio = new DoacaoDAL();

    public boolean cadastrar(Doacao doacao) {
        if (doacao == null || doacao.getEmail() == null || doacao.getEmail().isEmpty()) {
            return false;
        }
        return repositorio.salvar(doacao);
    }

    public boolean atualizar(Doacao doacao) {
        if (doacao == null || doacao.getId() == null) {
            return false;
        }
        return repositorio.atualizar(doacao);
    }

    public boolean deletar(Long id) {
        if (id == null) return false;
        Doacao doacao = repositorio.buscarPorId(id);
        if (doacao == null) return false;
        return repositorio.deletar(doacao);
    }

    public List<Doacao> listarTodas() {
        return repositorio.   listarTodas();
    }

    public List<Doacao> buscarPorEmail(String email) {
        if (email == null || email.isEmpty()) {
            return List.of();
        }
        return repositorio.buscarPorEmail(email);
    }

    public Doacao buscarPorId(Long id) {
        if (id == null) return null;
        return repositorio.buscarPorId(id);
    }
}
