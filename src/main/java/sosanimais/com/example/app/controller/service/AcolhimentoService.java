package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.AcolhimentoDAL;
import sosanimais.com.example.app.model.entity.Acolhimento;
import sosanimais.com.example.app.model.entity.Animal;
import sosanimais.com.example.app.model.entity.Funcionario;

import java.sql.SQLException;
import java.util.List;
@Service
public class AcolhimentoService {
    @Autowired
    private AcolhimentoDAL acolhimentoDAL;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private FuncionarioService funcionarioService;

    public Acolhimento salvarAcolhimento(Acolhimento ac) {
        Animal animal=animalService.buscarPorId(ac.getIdAnimal());
        Acolhimento existe=acolhimentoDAL.buscarPorAnimal(ac.getIdAnimal());
        Funcionario funcionario=funcionarioService.getId(ac.getIdFunc());
        if(animal==null)
            throw new IllegalStateException("Animal com id " + ac.getIdAnimal() + " não encontrado.");
        if(existe!=null)
            throw new IllegalStateException("Não foi possível registrar acolhimento, pois o animal já está presente no acolhimento de id: " + existe.getId());
        if(funcionario==null)
            throw new IllegalStateException("Funcionario com id " + ac.getIdFunc() + " não encontrado.");

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
        Animal animal=animalService.buscarPorId(ac.getIdAnimal());
        Acolhimento existe=acolhimentoDAL.buscarPorAnimal(ac.getIdAnimal());
        Funcionario funcionario=funcionarioService.getId(ac.getIdFunc());
        if (existe!= null&&!existe.getId().equals(ac.getId()))
            throw new IllegalStateException("Não foi possível registrar acolhimento, pois o animal já está presente no acolhimento de id: " + existe.getId());
        if(funcionario==null)
            throw new IllegalStateException("Funcionario com id " + ac.getIdFunc() + " não encontrado.");
        if(animal==null)
            throw new IllegalStateException("Animal com id " + ac.getIdAnimal() + " não encontrado.");
        boolean att = acolhimentoDAL.update(ac);
        if (att) {
            return ac;
        }
        return null;
    }

    public boolean deletarAcolhimento(Acolhimento ac) {
        return acolhimentoDAL.delete(ac);
    }

    public Acolhimento buscarPorId(Long id) {
        return acolhimentoDAL.get(id);
    }

    public List<Acolhimento>buscarPorNomeAnimal(String nome){
        return acolhimentoDAL.buscarPorNomeAnimal(nome);
    }

    public List<Acolhimento> buscarTodos() throws SQLException {
        return acolhimentoDAL.get("");
    }
}
