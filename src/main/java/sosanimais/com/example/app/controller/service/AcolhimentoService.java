package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.AcolhimentoDAL;
import sosanimais.com.example.app.model.entity.Acolhimento;

import java.sql.SQLException;
import java.util.List;
@Service
public class AcolhimentoService {
    @Autowired
    private AcolhimentoDAL acolhimentoDAL;

    public Acolhimento salvarAcolhimento(Acolhimento ac) {
        boolean save = acolhimentoDAL.save(ac);
        if(save==true)
            return ac;
        return null;
    }

    public Acolhimento atualizarAcolhimento(Acolhimento ac) {
        boolean att=acolhimentoDAL.update(ac);
        if(att==true)
            return ac;
        return null;
    }

    public boolean deletarAcolhimento(Acolhimento ac) {
        return acolhimentoDAL.delete(ac);
    }

    public Acolhimento buscarPorId(Long id) {
        return acolhimentoDAL.get(id);
    }

    public List<Acolhimento> buscarTodos() throws SQLException {
        return acolhimentoDAL.get("");
    }
}
