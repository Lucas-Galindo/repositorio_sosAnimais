package sosanimais.com.example.app.model.DAL;


import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.Adocao_Animal;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import java.text.SimpleDateFormat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdocaoDAL implements IDAL<Adocao_Animal> {
    public AdocaoDAL() {super();}

    @Override
    public boolean save(Adocao_Animal entidade) {
        System.out.println("=== INÍCIO DO PROCESSO DE SALVAR ADOÇÃO ===");
        System.out.println("Dados recebidos:");
        System.out.println("ID da adoção: " + entidade.getAdoCod());
        System.out.println("Data: " + entidade.getAdoData());
        System.out.println("ID do funcionário: " + entidade.getFuncCod());
        System.out.println("ID do animal: " + entidade.getAniCod());
        System.out.println("Matrícula do adotante: " + entidade.getAdoMat());

        // Buscar o último ID da tabela
        String getLastId = "SELECT MAX(adota_cod) as ultimo_id FROM adocao_animal";
        System.out.println("Buscando último ID...");
        ResultSet rs = SingletonDB.getConexao().consultar(getLastId);
        try {
            if (rs != null && rs.next()) {
                Long ultimoId = rs.getLong("ultimo_id");
                entidade.setAdoCod(ultimoId + 1);
                System.out.println("Último ID obtido: " + ultimoId + ", próximo será: " + (ultimoId + 1));
            } else {
                entidade.setAdoCod(1L);
                System.out.println("Primeiro registro, ID será: 1");
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao obter último ID: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataAdocao = sdf.format(entidade.getAdoData());

        String sql = """
                    INSERT INTO adocao_animal(adota_cod, adota_data, funcionario_func_cod, animal_ani_cod, adotante_mat)
                    VALUES (#1, '#2', #3, #4, #5)
                """;
        System.out.println("DataSaida: " + dataAdocao);
        System.out.println("FuncCod: " + entidade.getFuncCod());
        System.out.println("IdAni: " + entidade.getAniCod());
        System.out.println("MatAdotante: " + entidade.getAdoMat());

        sql = sql.replace("#1", String.valueOf(entidade.getAdoCod()));
        sql = sql.replace("#2", dataAdocao);
        sql = sql.replace("#3", String.valueOf(entidade.getFuncCod()));
        sql = sql.replace("#4", String.valueOf(entidade.getAniCod()));
        sql = sql.replace("#5", String.valueOf(entidade.getAdoMat()));
        System.out.println("Enviando o SQL: " + sql);

        // Primeiro atualiza o status do animal
        String AniStatusUpd = """
                    UPDATE animal SET ani_status = 'A' WHERE ani_cod = #1;
                """;
        AniStatusUpd = AniStatusUpd.replace("#1", String.valueOf(entidade.getAniCod()));
        System.out.println("Atualizando status do animal: " + AniStatusUpd);
        boolean statusUpdated = SingletonDB.getConexao().manipular(AniStatusUpd);
        
        if (!statusUpdated) {
            System.out.println("Erro ao atualizar status do animal");
            return false;
        }

        // Depois insere a adoção
        boolean sucesso = SingletonDB.getConexao().manipular(sql);
        if (!sucesso) {
            // Se falhar a inserção, reverte o status do animal
            String revertStatus = """
                        UPDATE animal SET ani_status = 'D' WHERE ani_cod = #1;
                    """;
            revertStatus = revertStatus.replace("#1", String.valueOf(entidade.getAniCod()));
            SingletonDB.getConexao().manipular(revertStatus);
            System.out.println("Erro ao inserir adoção. Status do animal revertido.");
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Adocao_Animal entidade) {
        String sql= """
                UPDATE adocao_animal SET adota_data='#2', funcionario_func_cod=#3, animal_ani_cod=#4, adotante_mat=#5
                WHERE adota_cod=#1
                """;
        sql=sql.replace("#1",""+entidade.getAdoCod());
        sql=sql.replace("#2",entidade.getAdoData().toString());
        sql=sql.replace("#3",String.valueOf(entidade.getFuncCod()));
        sql=sql.replace("#4",String.valueOf(entidade.getAniCod()));
        sql=sql.replace("#5",String.valueOf(entidade.getAdoMat()));
        System.out.println("Enviando o SQL: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Adocao_Animal entidade) {
        try {
            // Busca informações necessárias
            String sql = "SELECT animal_ani_cod, adotante_mat FROM adocao_animal WHERE adota_cod = " + entidade.getAdoCod();
            ResultSet rs = SingletonDB.getConexao().consultar(sql);
            
            if (rs != null && rs.next()) {
                Long aniCod = rs.getLong("animal_ani_cod");
                int adoMat = rs.getInt("adotante_mat");

                // Atualiza status do animal para disponível
                SingletonDB.getConexao().manipular("UPDATE animal SET ani_status = 'D' WHERE ani_cod = " + aniCod);
                
                // Remove referência no adotante
                SingletonDB.getConexao().manipular("UPDATE adotante SET adocao_animal_adota_cod = NULL WHERE adotante_matricula = " + adoMat);
                
                // Exclui a adoção
                return SingletonDB.getConexao().manipular("DELETE FROM adocao_animal WHERE adota_cod = " + entidade.getAdoCod());
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Adocao_Animal get(Long id) {//ok
        Adocao_Animal adocao = null;
        String sql = "SELECT * FROM adocao_animal WHERE adota_cod=" + id;
        System.out.println("SQL: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs!=null && rs.next()) {
                adocao = new Adocao_Animal(
                        rs.getLong("adota_cod"),
                        rs.getLong("funcionario_func_cod"),
                        rs.getLong("animal_ani_cod"),
                        rs.getInt("adotante_mat"),
                        rs.getDate("adota_data")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adocao;
    }

    @Override
    public List<Adocao_Animal> get(String filtro) throws SQLException {
        List<Adocao_Animal> adocao = new ArrayList<>();
        String sql = "SELECT * FROM adocao_animal";
        if (!filtro.isEmpty())
            sql += " WHERE " + filtro;

        System.out.println("SQL de busca: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                Adocao_Animal ad = new Adocao_Animal(
                        rs.getLong("adota_cod"),
                        rs.getLong("funcionario_func_cod"),
                        rs.getLong("animal_ani_cod"),
                        rs.getInt("adotante_mat"),
                        rs.getDate("adota_data")
                );
                adocao.add(ad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adocao;
    }
}
