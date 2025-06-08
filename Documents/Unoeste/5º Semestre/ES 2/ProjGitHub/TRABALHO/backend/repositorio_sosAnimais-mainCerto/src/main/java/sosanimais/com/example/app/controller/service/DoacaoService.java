package sosanimais.com.example.app.controller.service;

import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.DoacaoDAL;
import sosanimais.com.example.app.model.entity.Doacao;

import java.util.Collections; // Import para lista vazia segura
import java.util.List;

@Service
public class DoacaoService {

    private final DoacaoDAL doacaoDAL;

    public DoacaoService() {
        // Se DoacaoDAL for um @Repository gerenciado pelo Spring,
        // o ideal seria injetá-lo via construtor com @Autowired.
        // Ex: @Autowired public DoacaoService(DoacaoDAL doacaoDAL) { this.doacaoDAL = doacaoDAL; }
        // Por enquanto, mantendo a instanciação direta que você tinha.
        this.doacaoDAL = new DoacaoDAL();
    }

    public Doacao registrarDoacao(Doacao doacao) {
        if (doacao == null || doacao.getNomeDoador() == null || doacao.getNomeDoador().trim().isEmpty() ||
                doacao.getEmail() == null || doacao.getEmail().trim().isEmpty() ||
                doacao.getTipo() == null || doacao.getTipo().trim().isEmpty() ||
                doacao.getDataDoacao() == null) {
            System.err.println("Dados da doação inválidos no serviço.");
            return null;
        }

        if ("dinheiro".equalsIgnoreCase(doacao.getTipo()) && (doacao.getValor() == null || doacao.getValor() <= 0)) {
            System.err.println("Valor inválido para doação em dinheiro.");
            return null;
        }

        if (("remedios".equalsIgnoreCase(doacao.getTipo()) || "comida".equalsIgnoreCase(doacao.getTipo()) || "produtos".equalsIgnoreCase(doacao.getTipo())) &&
                (doacao.getProduto() == null || doacao.getProduto().getNome() == null || doacao.getProduto().getNome().trim().isEmpty())) {
            System.err.println("Nome do produto obrigatório para este tipo de doação.");
            return null;
        }
        return doacaoDAL.save(doacao);
    }

    // NOVO MÉTODO: Listar todas as doações
    public List<Doacao> listarTodasDoacoes() {
        try {
            return doacaoDAL.getAll(); // Este método precisará ser criado no DoacaoDAL
        } catch (Exception e) {
            System.err.println("Erro no serviço ao listar todas as doações: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList(); // Retorna lista vazia em caso de erro
        }
    }

    // NOVO MÉTODO: Buscar doação por ID (para o endpoint GET /{id})
    public Doacao buscarDoacaoPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        try {
            return doacaoDAL.getById(id); // Este método precisará ser criado no DoacaoDAL
        } catch (Exception e) {
            System.err.println("Erro no serviço ao buscar doação por ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // MÉTODO EXISTENTE: Deletar doação
    public boolean deletarDoacao(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        try {
            // Adicionar lógica para verificar se a doação existe antes de tentar deletar, se desejado
            // Ex: if (doacaoDAL.getById(id) == null) return false;
            return doacaoDAL.delete(id);
        } catch (Exception e) {
            System.err.println("Erro no serviço ao deletar doação com ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
