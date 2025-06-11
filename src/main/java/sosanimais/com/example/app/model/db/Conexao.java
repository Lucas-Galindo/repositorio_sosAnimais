package sosanimais.com.example.app.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private Connection connect;
    private String erro;

    // Para guardar os últimos parâmetros de conexão bem-sucedidos
    private String lastLocal;
    private String lastBanco;
    private String lastUsuario;
    private String lastSenha;
    private boolean hasConnectedSuccessfullyOnce = false;

    public Conexao() {
        erro = "";
        connect = null;
    }

    // Este método agora retorna o java.sql.Connection diretamente
    // e tenta reconectar se necessário.
    public Connection getConnect() {
        try {
            if (connect == null || connect.isClosed()) {
                System.out.println("Conexão está fechada ou nula. Tentando reconectar...");
                if (hasConnectedSuccessfullyOnce && lastLocal != null && lastBanco != null && lastUsuario != null && lastSenha != null) {
                    // Tenta reconectar usando os últimos parâmetros válidos
                    String url = lastLocal + lastBanco;
                    connect = DriverManager.getConnection(url, lastUsuario, lastSenha);
                    if (connect != null && !connect.isClosed()) {
                        System.out.println("Reconexão bem-sucedida!");
                        erro = ""; // Limpa erro anterior se houver
                    } else {
                        erro = "Falha ao reconectar com os parâmetros anteriores.";
                        connect = null; // Garante que connect é null se a reconexão falhar
                    }
                } else {
                    erro = "Não é possível reconectar: dados de conexão iniciais não foram estabelecidos ou estão faltando.";
                    System.err.println(erro);
                    connect = null;
                }
            }
        } catch (SQLException sqlex) {
            erro = "Exceção ao verificar/recriar conexão: " + sqlex.toString();
            System.err.println(erro);
            sqlex.printStackTrace();
            connect = null; // Garante que connect é null se a exceção ocorrer
        }
        return connect;
    }

    public boolean conectar(String local, String banco, String usuario, String senha) {
        boolean conectado = false;
        try {
            // Guardar os parâmetros para futuras reconexões
            this.lastLocal = local;
            this.lastBanco = banco;
            this.lastUsuario = usuario;
            this.lastSenha = senha;

            String url = local + banco; // Ex: "jdbc:postgresql://localhost:5432/" + "Sos_animais_definitivo"
            System.out.println("Tentando conectar a: " + url);
            connect = DriverManager.getConnection(url, usuario, senha);
            conectado = true;
            hasConnectedSuccessfullyOnce = true; // Marca que uma conexão bem-sucedida ocorreu
            erro = ""; // Limpa erros anteriores
            System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso.");
        } catch (SQLException sqlex) {
            erro = "Impossivel conectar com a base de dados: " + sqlex.toString();
            System.err.println(erro);
            sqlex.printStackTrace();
            hasConnectedSuccessfullyOnce = false;
        } catch (Exception ex) {
            erro = "Outro erro ao conectar: " + ex.toString();
            System.err.println(erro);
            ex.printStackTrace();
            hasConnectedSuccessfullyOnce = false;
        }
        return conectado;
    }

    public String getMensagemErro() {
        return erro;
    }

    public boolean getEstadoConexao() {
        try {
            return (connect != null && !connect.isClosed());
        } catch (SQLException e) {
            return false;
        }
    }

    // Os métodos manipular e consultar devem usar a conexão obtida por getConnect()
    // e idealmente seriam reescritos para usar PreparedStatement para segurança.
    // Por enquanto, eles usarão a conexão como está.

    public boolean manipular(String sql) { // inserir, alterar,excluir
        boolean executou = false;
        Connection currentConnection = getConnect(); // Garante que temos uma conexão válida
        if (currentConnection == null) {
            erro = "Falha ao executar manipulação: conexão nula.";
            System.err.println(erro);
            return false;
        }
        try (Statement statement = currentConnection.createStatement()) {
            int result = statement.executeUpdate(sql);
            if (result >= 1)
                executou = true;
        } catch (SQLException sqlex) {
            erro = "Erro ao manipular: " + sqlex.toString();
            System.err.println(erro);
            sqlex.printStackTrace();
        }
        return executou;
    }

    public ResultSet consultar(String sql) {
        ResultSet rs = null;
        Connection currentConnection = getConnect(); // Garante que temos uma conexão válida
        if (currentConnection == null) {
            erro = "Falha ao executar consulta: conexão nula.";
            System.err.println(erro);
            return null;
        }
        try {
            // O Statement e o ResultSet devem ser fechados pelo chamador
            // ou o Statement deve ser fechado aqui e o ResultSet retornado.
            // Para compatibilidade com seu DAL que usa try-with-resources para ResultSet,
            // retornamos o ResultSet aberto, mas o Statement será fechado implicitamente
            // se o chamador fechar o ResultSet e a Connection.
            // Idealmente, o Statement também seria gerenciado com try-with-resources no chamador.
            Statement statement = currentConnection.createStatement();
            rs = statement.executeQuery(sql);
        } catch (SQLException sqlex) {
            erro = "Erro ao consultar: " + sqlex.toString();
            System.err.println(erro);
            sqlex.printStackTrace();
            // rs já é null
        }
        return rs;
    }

    public int getMaxPK(String tabela, String chave) {
        String sql = "select max(" + chave + ") from " + tabela;
        int max = 0;
        // Usar try-with-resources para o ResultSet aqui é uma boa prática
        try (ResultSet rs = consultar(sql)) { // consultar agora garante uma conexão válida
            if (rs != null && rs.next()) {
                max = rs.getInt(1);
            } else if (rs == null) {
                // Erro já foi logado por consultar()
                max = -1;
            }
        } catch (SQLException sqlex) {
            erro = "Erro ao buscar MaxPK: " + sqlex.toString();
            System.err.println(erro);
            sqlex.printStackTrace();
            max = -1;
        }
        return max;
    }
}
