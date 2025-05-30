package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.ProdutoInformacao;
// Certifique-se de que SingletonDB.getConexao() retorna uma instância de sosanimais.com.example.app.model.db.Conexao
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAL {

    public boolean save(Produto produto) {
        ProdutoInformacao info = produto.getProduto();
        String sql = "INSERT INTO produto (produto_nome, produto_descricao, produto_preco, produto_validade) VALUES (?, ?, ?, ?)";

        try (Connection conn = SingletonDB.getConexao().getConnect(); // MODIFICADO AQUI para usar getConnect()
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, info.getNome());
            pstmt.setString(2, info.getDescricao());
            pstmt.setDouble(3, info.getPreco());
            pstmt.setString(4, info.getValidade());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            // Adicionar tratamento de erro mais específico ou log, se necessário
            return false;
        } catch (NullPointerException e) {
            // Pode ocorrer se SingletonDB.getConexao() ou info for null
            e.printStackTrace();
            return false;
        }
    }

    public Produto get(int id) {
        String sql = "SELECT * FROM produto WHERE produto_id = ?";
        ResultSet rs = null;
        try (Connection conn = SingletonDB.getConexao().getConnect(); // MODIFICADO AQUI
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ProdutoInformacao info = new ProdutoInformacao(
                        rs.getString("produto_nome"),
                        rs.getString("produto_descricao"),
                        rs.getDouble("produto_preco"),
                        rs.getString("produto_validade") // Assegure que a coluna existe no BD
                );
                return new Produto(rs.getInt("produto_id"), info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) try{ rs.close(); } catch(SQLException s){ s.printStackTrace(); }
        }
        return null;
    }

    public List<Produto> getAll() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        
        try (Connection conn = SingletonDB.getConexao().getConnect(); // MODIFICADO AQUI
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()){

            while (rs.next()) {
                ProdutoInformacao info = new ProdutoInformacao(
                        rs.getString("produto_nome"),
                        rs.getString("produto_descricao"),
                        rs.getDouble("produto_preco"),
                        rs.getString("produto_validade") // Assegure que a coluna existe no BD
                );
                lista.add(new Produto(rs.getInt("produto_id"), info));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM produto WHERE produto_id = ?";
         try (Connection conn = SingletonDB.getConexao().getConnect(); // MODIFICADO AQUI
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Produto> getByName(String nome) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE LOWER(produto_nome) LIKE LOWER(?)";
        ResultSet rs = null;
        try (Connection conn = SingletonDB.getConexao().getConnect(); // MODIFICADO AQUI
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nome + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                 ProdutoInformacao info = new ProdutoInformacao(
                        rs.getString("produto_nome"),
                        rs.getString("produto_descricao"),
                        rs.getDouble("produto_preco"),
                        rs.getString("produto_validade") // Assegure que a coluna existe no BD
                );
                lista.add(new Produto(rs.getInt("produto_id"), info));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) try{ rs.close(); } catch(SQLException s){ s.printStackTrace(); }
        }
        return lista;
    }

    public List<Produto> getByValidade(String validade) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE produto_validade = ?"; // Assegure que a coluna existe no BD
        ResultSet rs = null; 
        try (Connection conn = SingletonDB.getConexao().getConnect(); // MODIFICADO AQUI
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, validade);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                 ProdutoInformacao info = new ProdutoInformacao(
                        rs.getString("produto_nome"),
                        rs.getString("produto_descricao"),
                        rs.getDouble("produto_preco"),
                        rs.getString("produto_validade")
                );
                lista.add(new Produto(rs.getInt("produto_id"), info));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) try{ rs.close(); } catch(SQLException s){ s.printStackTrace(); }
        }
        return lista;
    }
}