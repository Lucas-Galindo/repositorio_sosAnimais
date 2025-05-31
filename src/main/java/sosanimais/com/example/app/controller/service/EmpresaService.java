package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.EmpresaDAL;
import sosanimais.com.example.app.model.entity.Empresa;

@Service
public class EmpresaService {

    private final EmpresaDAL repositorio; // Final e injeção pelo construtor

    @Autowired
    public EmpresaService(EmpresaDAL repositorio) { // Injeção pelo construtor
        this.repositorio = repositorio;
    }

    public boolean cadastro(Empresa entidade){
        // Validações de negócio podem ser adicionadas aqui
        // Ex: verificar se o CNPJ já existe se não for para atualizar
        return repositorio.save(entidade);
    }

    public Empresa getId(Long id){
        return repositorio.get(id);
    }

    public boolean deletar(Empresa entidade){
        // Regra de negócio: talvez não permitir deletar a (única) empresa?
        return repositorio.delete(entidade);
    }

    public boolean atualizar(Empresa entidade){
        return repositorio.update(entidade);
    }

    public Empresa verificarEmpresaExistente() {
        return repositorio.buscarUnicaEmpresa();
    }

    // Métodos que faltavam para o controller chamar
    public Empresa buscarEmpresaPorCnpj(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return null;
        }
        return repositorio.buscarPorCnpj(cnpj);
    }

    public Empresa buscarEmpresaPorCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            return null;
        }
        return repositorio.buscarPorCep(cep);
    }
}
