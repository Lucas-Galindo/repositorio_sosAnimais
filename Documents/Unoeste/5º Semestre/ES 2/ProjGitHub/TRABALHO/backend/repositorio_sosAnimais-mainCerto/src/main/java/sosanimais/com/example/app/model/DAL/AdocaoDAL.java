package sosanimais.com.example.app.model.DAL;


import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.Adocao_Animal;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

@Repository
public class AdocaoDAL implements IDAL<Adocao_Animal> {
    public AdocaoDAL() {super();}

    @Override
    public boolean save(Adocao_Animal entidade) {
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
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String dataAdocao = sdf.format(entidade.getAdoData());

        String sql = """
                    INSERT INTO adocao_animal(adota_cod, adota_data, funcionario_func_cod, animal_ani_cod, adotante_mat)
                    VALUES (#1, '#2', #3, #4, #5)
                """;

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

        // Atualiza o campo adocao_animal_adota_cod do adotante
        String updateAdotante = """
                    UPDATE adotante SET adocao_animal_adota_cod = #1 WHERE adotante_matricula = #2;
                """;
        updateAdotante = updateAdotante.replace("#1", String.valueOf(entidade.getAdoCod()));
        updateAdotante = updateAdotante.replace("#2", String.valueOf(entidade.getAdoMat()));
        System.out.println("Atualizando adotante: " + updateAdotante);
        
        boolean adotanteUpdated = SingletonDB.getConexao().manipular(updateAdotante);
        if (!adotanteUpdated) {
            // Se falhar a atualização do adotante, reverte tudo
            String revertAnimal = """
                        UPDATE animal SET ani_status = 'D' WHERE ani_cod = #1;
                    """;
            revertAnimal = revertAnimal.replace("#1", String.valueOf(entidade.getAniCod()));
            SingletonDB.getConexao().manipular(revertAnimal);
            
            String deleteAdocao = """
                        DELETE FROM adocao_animal WHERE adota_cod = #1;
                    """;
            deleteAdocao = deleteAdocao.replace("#1", String.valueOf(entidade.getAdoCod()));
            SingletonDB.getConexao().manipular(deleteAdocao);
            
            System.out.println("Erro ao atualizar adotante. Operação revertida.");
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Adocao_Animal entidade) {
        try {
            // Buscar dados atuais da adoção
            String sqlBusca = "SELECT animal_ani_cod, adotante_mat FROM adocao_animal WHERE adota_cod = " + entidade.getAdoCod();
            ResultSet rs = SingletonDB.getConexao().consultar(sqlBusca);
            
            Long animalAntigoId = null;
            int adotanteAntigoMat = 0;
            if (rs != null && rs.next()) {
                animalAntigoId = rs.getLong("animal_ani_cod");
                adotanteAntigoMat = rs.getInt("adotante_mat");
            } else {
                System.out.println("Adoção não encontrada para atualização");
                return false;
            }

            // Atualizar status dos animais se necessário
            if (!animalAntigoId.equals(entidade.getAniCod())) {
                String sqlVerificaAnimal = "SELECT ani_status FROM animal WHERE ani_cod = " + entidade.getAniCod();
                ResultSet rsAnimal = SingletonDB.getConexao().consultar(sqlVerificaAnimal);
                
                if (rsAnimal != null && rsAnimal.next()) {
                    String statusNovoAnimal = rsAnimal.getString("ani_status");
                    if (!"D".equals(statusNovoAnimal)) {
                        System.out.println("O novo animal não está disponível para adoção");
                        return false;
                    }
                } else {
                    System.out.println("Novo animal não encontrado");
                    return false;
                }

                String sqlAnimalAntigo = "UPDATE animal SET ani_status = 'D' WHERE ani_cod = " + animalAntigoId;
                if (!SingletonDB.getConexao().manipular(sqlAnimalAntigo)) {
                    System.out.println("Erro ao atualizar status do animal antigo");
                    return false;
                }

                String sqlAnimalNovo = "UPDATE animal SET ani_status = 'A' WHERE ani_cod = " + entidade.getAniCod();
                if (!SingletonDB.getConexao().manipular(sqlAnimalNovo)) {
                    String sqlReverteAntigo = "UPDATE animal SET ani_status = 'A' WHERE ani_cod = " + animalAntigoId;
                    SingletonDB.getConexao().manipular(sqlReverteAntigo);
                    System.out.println("Erro ao atualizar status do novo animal");
                    return false;
                }
            }

            // Formatar data e atualizar adoção
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
            String dataAdocao = sdf.format(entidade.getAdoData());

            String sqlUpdateAdocao = "UPDATE adocao_animal SET adota_data = '" + dataAdocao + 
                                   "', funcionario_func_cod = " + entidade.getFuncCod() + 
                                   ", animal_ani_cod = " + entidade.getAniCod() + 
                                   ", adotante_mat = " + entidade.getAdoMat() + 
                                   " WHERE adota_cod = " + entidade.getAdoCod();

            if (!SingletonDB.getConexao().manipular(sqlUpdateAdocao)) {
                if (!animalAntigoId.equals(entidade.getAniCod())) {
                    String sqlReverteAnimais = "UPDATE animal SET ani_status = 'A' WHERE ani_cod = " + animalAntigoId + ";" +
                                             "UPDATE animal SET ani_status = 'D' WHERE ani_cod = " + entidade.getAniCod();
                    SingletonDB.getConexao().manipular(sqlReverteAnimais);
                }
                System.out.println("Erro ao atualizar adoção");
                return false;
            }

            // Atualizar referências dos adotantes
            if (adotanteAntigoMat != entidade.getAdoMat()) {
                String sqlAdotanteAntigo = "UPDATE adotante SET adocao_animal_adota_cod = NULL WHERE adotante_matricula = " + adotanteAntigoMat;
                SingletonDB.getConexao().manipular(sqlAdotanteAntigo);
            }

            String sqlAdotanteNovo = "UPDATE adotante SET adocao_animal_adota_cod = " + entidade.getAdoCod() + 
                                   " WHERE adotante_matricula = " + entidade.getAdoMat();
            if (!SingletonDB.getConexao().manipular(sqlAdotanteNovo)) {
                if (!animalAntigoId.equals(entidade.getAniCod())) {
                    String sqlReverteAnimais = "UPDATE animal SET ani_status = 'A' WHERE ani_cod = " + animalAntigoId + ";" +
                                             "UPDATE animal SET ani_status = 'D' WHERE ani_cod = " + entidade.getAniCod();
                    SingletonDB.getConexao().manipular(sqlReverteAnimais);
                }
                
                String sqlReverteAdocao = "UPDATE adocao_animal SET " +
                                        "adota_data = '" + dataAdocao + "', " +
                                        "funcionario_func_cod = " + entidade.getFuncCod() + ", " +
                                        "animal_ani_cod = " + animalAntigoId + ", " +
                                        "adotante_mat = " + adotanteAntigoMat + " " +
                                        "WHERE adota_cod = " + entidade.getAdoCod();
                SingletonDB.getConexao().manipular(sqlReverteAdocao);
                
                if (adotanteAntigoMat != entidade.getAdoMat()) {
                    String sqlReverteAdotanteAntigo = "UPDATE adotante SET adocao_animal_adota_cod = " + entidade.getAdoCod() + 
                                                     " WHERE adotante_matricula = " + adotanteAntigoMat;
                    SingletonDB.getConexao().manipular(sqlReverteAdotanteAntigo);
                }
                
                System.out.println("Erro ao atualizar referência do adotante");
                return false;
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Erro durante a atualização: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
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
    public Adocao_Animal get(Long id) {
        Adocao_Animal adocao = null;
        String sql = "SELECT * FROM adocao_animal WHERE adota_cod=" + id;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs!=null && rs.next()) {
                Timestamp timestamp = rs.getTimestamp("adota_data");
                Date dataConvertida = new Date(timestamp.getTime());
                
                adocao = new Adocao_Animal(
                    rs.getLong("adota_cod"),
                    rs.getLong("funcionario_func_cod"),
                    rs.getLong("animal_ani_cod"),
                    rs.getInt("adotante_mat"),
                    dataConvertida
                );
            }
        } catch (SQLException e) {
            System.out.println("[ERRO] Erro ao buscar adoção: " + e.getMessage());
            e.printStackTrace();
        }
        return adocao;
    }

    @Override
    public List<Adocao_Animal> get(String filtro) throws SQLException {
        List<Adocao_Animal> adocao = new ArrayList<>();
        String sql = "SELECT * FROM adocao_animal";
        if (!filtro.isEmpty()) {
            sql += " WHERE " + filtro;
        }
        sql += " ORDER BY adota_cod ASC";

        System.out.println("[DEBUG] SQL de busca: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                java.sql.Date dataBanco = rs.getDate("adota_data");
                System.out.println("[DEBUG] Data exata do banco: " + dataBanco);
                System.out.println("[DEBUG] Data toString: " + dataBanco.toString());
                
                Adocao_Animal ad = new Adocao_Animal(
                    rs.getLong("adota_cod"),
                    rs.getLong("funcionario_func_cod"),
                    rs.getLong("animal_ani_cod"),
                    rs.getInt("adotante_mat"),
                    dataBanco
                );
                adocao.add(ad);
            }
        } catch (SQLException e) {
            System.out.println("[ERRO] Erro ao buscar adoções: " + e.getMessage());
            e.printStackTrace();
        }
        return adocao;
    }
}
