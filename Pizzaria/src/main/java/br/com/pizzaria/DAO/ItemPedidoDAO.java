package br.com.pizzaria.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.pizzaria.model.ItemCardapioModel;
import br.com.pizzaria.model.ItemPedidoModel;
import br.com.pizzaria.model.MesaModel;
import br.com.pizzaria.model.PedidoModel;

public class ItemPedidoDAO {

    private Connection conexao;

    public ItemPedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void criarItemPedido(ItemPedidoModel item) throws SQLException {
        String sql = "INSERT INTO Item_pedido (Foreign_ID_pedido, Foreign_ID_cardapio, qtd_pedido, valor_unitario, valor_total) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, item.getID_Pedido().getID_Pedido());
            stmt.setInt(2, item.getID_Cardapio().getID_Cardapio());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnitario());
            stmt.setDouble(5, item.getPrecoTotal());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setID_Item(generatedKeys.getInt(1));
                }
            }
        }
    }

    // 🔹 Lista todos os itens
    public List<ItemPedidoModel> listarItensPedido() throws SQLException {
        return listarItensPorMesa(null); // retorna todos quando sem filtro
    }

    public List<ItemPedidoModel> listarItensPorMesa(Integer numeroMesa) throws SQLException {
        List<ItemPedidoModel> itens = new ArrayList<>();

        String sql = "SELECT ip.ID_item, ip.qtd_pedido, ip.valor_unitario, ip.valor_total, " +
                     "p.ID_pedido, p.hora_pedido, p.status_pedido, m.numero_mesa, " +
                     "c.ID_Cardapio, c.nome_cardapio, c.preco_cardapio " +
                     "FROM Item_pedido ip " +
                     "INNER JOIN Pedido p ON ip.Foreign_ID_pedido = p.ID_pedido " +
                     "INNER JOIN Mesa m ON p.Foreign_numero_mesa = m.numero_mesa " +
                     "INNER JOIN Cardapio_Item c ON ip.Foreign_ID_cardapio = c.ID_cardapio";

        if (numeroMesa != null) {
            sql += " WHERE m.numero_mesa = ?";
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            if (numeroMesa != null) {
                stmt.setInt(1, numeroMesa);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Cardápio
                    ItemCardapioModel cardapio = new ItemCardapioModel();
                    cardapio.setID_Cardapio(rs.getInt("ID_Cardapio"));
                    cardapio.setNome(rs.getString("nome_cardapio"));
                    cardapio.setPreco(rs.getDouble("preco_cardapio"));

                    // Mesa
                    MesaModel mesa = new MesaModel();
                    mesa.setNumeroMesa(rs.getInt("numero_mesa"));

                    // Pedido
                    PedidoModel pedido = new PedidoModel();
                    pedido.setID_Pedido(rs.getInt("ID_pedido"));
                    pedido.setHoraPedido(rs.getTimestamp("hora_pedido").toLocalDateTime());
                    pedido.setStatusPedido(rs.getBoolean("status_pedido"));
                    pedido.setID_Mesa(mesa);

                    // ItemPedido
                    ItemPedidoModel item = new ItemPedidoModel();
                    item.setID_Item(rs.getInt("ID_item"));
                    item.setQuantidade(rs.getInt("qtd_pedido"));
                    item.setPrecoUnitario(rs.getDouble("valor_unitario"));
                    item.setPrecoTotal(rs.getDouble("valor_total"));
                    item.setID_Pedido(pedido);
                    item.setID_Cardapio(cardapio);

                    itens.add(item);
                }
            }
        }
        return itens;
    }
    public List<Integer> listarMesasExistentes() throws SQLException {
        List<Integer> mesas = new ArrayList<>();
        String sql = "SELECT DISTINCT numero_mesa FROM Mesa ORDER BY numero_mesa";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                mesas.add(rs.getInt("numero_mesa"));
            }
        }
        return mesas;
    }

    // 🔹 Lista itens filtrando por ID do pedido
    public List<ItemPedidoModel> listarItensPorPedido(int idPedido) throws SQLException {
        List<ItemPedidoModel> itens = new ArrayList<>();

        String sql = "SELECT ip.ID_item, ip.qtd_pedido, ip.valor_unitario, ip.valor_total, " +
                "p.ID_pedido, p.hora_pedido, p.status_pedido, m.numero_mesa, " +
                "c.ID_Cardapio, c.nome_cardapio, c.preco_cardapio " +
                "FROM Item_pedido ip " +
                "INNER JOIN Pedido p ON ip.Foreign_ID_pedido = p.ID_pedido " +
                "INNER JOIN Mesa m ON p.Foreign_numero_mesa = m.numero_mesa " +
                "INNER JOIN Cardapio_Item c ON ip.Foreign_ID_cardapio = c.ID_cardapio " +
                "WHERE p.ID_pedido = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    itens.add(mapResultSetToItemPedido(rs));
                }
            }
        }

        return itens;
    }

    // 🔹 Lista itens filtrando por número da mesa
    public List<ItemPedidoModel> listarItensPorMesa(int numeroMesa) throws SQLException {
        List<ItemPedidoModel> itens = new ArrayList<>();

        String sql = "SELECT ip.ID_item, ip.qtd_pedido, ip.valor_unitario, ip.valor_total, " +
                "p.ID_pedido, p.hora_pedido, p.status_pedido, m.numero_mesa, " +
                "c.ID_Cardapio, c.nome_cardapio, c.preco_cardapio " +
                "FROM Item_pedido ip " +
                "INNER JOIN Pedido p ON ip.Foreign_ID_pedido = p.ID_pedido " +
                "INNER JOIN Mesa m ON p.Foreign_numero_mesa = m.numero_mesa " +
                "INNER JOIN Cardapio_Item c ON ip.Foreign_ID_cardapio = c.ID_cardapio " +
                "WHERE m.numero_mesa = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, numeroMesa);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    itens.add(mapResultSetToItemPedido(rs));
                }
            }
        }

        return itens;
    }

    public void alterarItemPedido(ItemPedidoModel item) throws SQLException {
        String sql = "UPDATE Item_pedido SET Foreign_ID_pedido = ?, Foreign_ID_cardapio = ?, qtd_pedido = ?, valor_unitario = ?, valor_total = ? WHERE ID_Item = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, item.getID_Pedido().getID_Pedido());
            stmt.setInt(2, item.getID_Cardapio().getID_Cardapio());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnitario());
            stmt.setDouble(5, item.getPrecoTotal());
            stmt.setInt(6, item.getID_Item());
            stmt.executeUpdate();
        }
    }

    public void excluirItemPedido(int id) throws SQLException {
        String sql = "DELETE FROM Item_pedido WHERE ID_Item = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // 🔹 Método auxiliar para mapear o ResultSet em ItemPedidoModel
    private ItemPedidoModel mapResultSetToItemPedido(ResultSet rs) throws SQLException {
        // Cardápio
        ItemCardapioModel cardapio = new ItemCardapioModel();
        cardapio.setID_Cardapio(rs.getInt("ID_Cardapio"));
        cardapio.setNome(rs.getString("nome_cardapio"));
        cardapio.setPreco(rs.getDouble("preco_cardapio"));

        // Mesa
        MesaModel mesa = new MesaModel();
        mesa.setNumeroMesa(rs.getInt("numero_mesa"));

        // Pedido
        PedidoModel pedido = new PedidoModel();
        pedido.setID_Pedido(rs.getInt("ID_pedido"));
        pedido.setHoraPedido(rs.getTimestamp("hora_pedido").toLocalDateTime());
        pedido.setStatusPedido(rs.getBoolean("status_pedido"));
        pedido.setID_Mesa(mesa);

        // ItemPedido
        ItemPedidoModel item = new ItemPedidoModel();
        item.setID_Item(rs.getInt("ID_item"));
        item.setQuantidade(rs.getInt("qtd_pedido"));
        item.setPrecoUnitario(rs.getDouble("valor_unitario"));
        item.setPrecoTotal(rs.getDouble("valor_total"));
        item.setID_Pedido(pedido);
        item.setID_Cardapio(cardapio);

        return item;
    }
}
