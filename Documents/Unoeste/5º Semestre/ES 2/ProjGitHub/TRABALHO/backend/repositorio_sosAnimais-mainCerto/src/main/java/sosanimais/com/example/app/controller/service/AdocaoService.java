package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.Adocao_Animal;
import sosanimais.com.example.app.model.DAL.AdocaoDAL;

import java.sql.SQLException;
import java.util.List;

@Service
public class AdocaoService {
    @Autowired
    private AdocaoDAL adocaoDAL;

    public Adocao_Animal salvarAdocao(Adocao_Animal ad){
        boolean save = adocaoDAL.save(ad);
        if(save==true) //Salvo informações de adoção e atualização de status do animal
            return ad;
        return null;
    }

    public Adocao_Animal atualizarAdocao(Adocao_Animal ad) {
        boolean att=adocaoDAL.update(ad);
        if(att==true)
            return ad;
        return null;
    }

    public boolean deletarAdocao(Adocao_Animal ad) {
        System.out.println("SERVICE: Iniciando exclusão de adoção");
        System.out.println("ID: " + ad.getAdoCod());
        System.out.println("Animal: " + ad.getAniCod());
        System.out.println("Adotante: " + ad.getAdoMat());
        
        try {
            boolean result = adocaoDAL.delete(ad);
            System.out.println("Resultado da exclusão no service: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("ERRO no service durante exclusão: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Adocao_Animal buscarPorId(Long id) {
        return adocaoDAL.get(id);
    }

    public List<Adocao_Animal> buscarTodos() throws SQLException {
        return adocaoDAL.get("");
    }

    public List<Adocao_Animal> buscarComFiltro(String filtro) throws SQLException {
        System.out.println("Buscando adoções com filtro: " + filtro);
        return adocaoDAL.get(filtro);
    }
}
