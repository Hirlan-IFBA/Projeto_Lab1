package br.com.pizzaria.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.pizzaria.model.ItemCardapioModel;

public class ItemCardapioDAO {

    private Connection conexao;

    public ItemCardapioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void criarItem(ItemCardapioModel item) throws SQLException {
        String sql = "INSERT INTO Cardapio_Item (nome_cardapio, preco_cardapio, descricao_cardapio) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, item.getNome());
            stmt.setDouble(2, item.getPreco());
            stmt.setString(3, item.getDescricao());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Erro ao adicionar item ao cardápio.");
            }
        }
    }

    public List<ItemCardapioModel> listarItens() throws SQLException {
        List<ItemCardapioModel> itens = new ArrayList<>();
        String sql = "SELECT * from Cardapio_Item";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ItemCardapioModel item = new ItemCardapioModel();
                item.setID_Cardapio(rs.getInt("ID_Cardapio"));
                item.setNome(rs.getString("nome_cardapio"));
                item.setPreco(rs.getDouble("preco_cardapio"));
                item.setDescricao(rs.getString("descricao_cardapio"));
                itens.add(item);
            }
        }
        return itens;
    }
    public ItemCardapioModel buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Cardapio_Item WHERE ID_cardapio = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ItemCardapioModel item = new ItemCardapioModel();
                    item.setID_Cardapio(rs.getInt("ID_cardapio"));
                    item.setNome(rs.getString("nome_cardapio")); 
                    item.setPreco(rs.getDouble("preco_cardapio"));
                    return item;
                }
            }
        }
        return null;
    }

    public void alterarItem(ItemCardapioModel item) throws SQLException {
        String sql = "UPDATE Cardapio_Item SET nome_cardapio = ?, preco_cardapio = ?, descricao_cardapio = ? WHERE ID_Cardapio = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, item.getNome());
            stmt.setDouble(2, item.getPreco());
            stmt.setString(3, item.getDescricao());
            stmt.setInt(4, item.getID_Cardapio());
            stmt.executeUpdate();
        }
    }

    public void excluirItem(int id) throws SQLException {
        String sql = "DELETE FROM Cardapio_Item WHERE ID_Cardapio = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
