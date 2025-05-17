package sosanimais.com.example.app.controller.service;

import org.apache.el.stream.Stream;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.EmpresaDAL;
import sosanimais.com.example.app.model.entity.Empresa;

import java.util.List;

@Service
public class EmpresaService {


    EmpresaDAL repositorio;

    {
        repositorio = new EmpresaDAL() {

            @Override
            public Stream stream() {
                return null;
            }

            @Override
            public boolean save(Void entidade) {
                return false;
            }

            @Override
            public boolean update(Void entidade) {
                return false;
            }

            @Override
            public boolean delete(Void entidade) {
                return false;
            }

            public List<EmpresaDAL> get(String filtro) {
                return List.of();
            }
        };
    }

    public boolean cadastro(Empresa entidade){
        return repositorio.save(entidade);
    }

    public EmpresaDAL getId(Long id){
        return repositorio;
    }

    public boolean deletar(Empresa entidade){
        return repositorio.delete(entidade);
    }

    public boolean atualizar(Empresa entidade){
        return repositorio.update(entidade);
    }




}
