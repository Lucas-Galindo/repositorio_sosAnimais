package sosanimais.com.example.app.controller.service;

import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.PessoaDAL;
import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.entity.Pessoa;

import java.sql.ResultSet;

@Service
public class PessoaService {
    PessoaDAL repositorio;


    public boolean cadastro(Pessoa entidade){
        return repositorio.save(entidade);
    }

    public Pessoa getById(Long id){
        return repositorio.get(id);
    }

    public PessoaInformacao getPessoaInfo(ResultSet resultSet) {
        try {
            return new PessoaInformacao(
                    resultSet.getString("pess_nome"),
                    resultSet.getString("pess_cpf"),
                    resultSet.getString("pess_telefone"),
                    resultSet.getString("pess_email")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
