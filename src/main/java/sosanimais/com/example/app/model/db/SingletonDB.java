package sosanimais.com.example.app.model.db;

public class SingletonDB {

        private static Conexao conexao = null;

        private SingletonDB() {
        }

        public static boolean conectar () {
            if (conexao == null) {
                conexao = new Conexao();
            }
            return conexao.conectar("jdbc:postgresql://localhost:5432/","Sos_animais_DB", "postgres", "postgres123");
        }

        public static Conexao getConexao () {
            return conexao;
        }


}
