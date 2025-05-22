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
        Acolhimento existe=acolhimentoDAL.buscarPorAnimal(ac.getIdAnimal());
        if(existe!=null){
            throw new IllegalStateException("Este animal já possui acolhimento (id="
                    + existe.getId() + ").");
        }
        boolean save = acolhimentoDAL.save(ac);
        if(save){
            Long idGerado=acolhimentoDAL.obterUltimoIdPorAnimal(ac.getIdAnimal());
            System.out.println("Id gerado: "+idGerado);
            if(idGerado!=null){
                ac.setId(idGerado);
                return ac;
            }
        }
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
