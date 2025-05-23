package sosanimais.com.example.app.model.db;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DBinit {
    @PostConstruct
    public void init() {
        boolean conectado = SingletonDB.conectar();
        if (conectado) {
            System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso.");
        }
        else {
            System.out.println("❌ Erro ao conectar ao banco de dados.");
            System.err.println("Detalhe: " + SingletonDB.getConexao().getMensagemErro());
        }
    }
}
