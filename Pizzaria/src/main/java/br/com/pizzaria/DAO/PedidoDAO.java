package br.com.pizzaria.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.pizzaria.model.MesaModel;
import br.com.pizzaria.model.PedidoModel;

public class PedidoDAO {

    private Connection conexao;

    public PedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void criarPedido(PedidoModel pedido) throws SQLException {
        String sql = "INSERT INTO Pedido (hora_pedido, status_pedido, Foreign_numero_mesa) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(pedido.getHoraPedido()));
            stmt.setBoolean(2, pedido.getStatusPedido());
            stmt.setInt(3, pedido.getID_Mesa().getNumeroMesa());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setID_Pedido(rs.getInt(1));
                }
            }
        }
    }

    public List<PedidoModel> listarPedidos() throws SQLException {
        List<PedidoModel> pedidos = new ArrayList<>();
        String sql = "SELECT ped.ID_pedido, ped.hora_pedido, ped.status_pedido, m.numero_mesa "
                   + "FROM Pedido AS ped "
                   + "INNER JOIN Mesa m ON ped.Foreign_numero_mesa = m.numero_mesa";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MesaModel mesa = new MesaModel();
                mesa.setNumeroMesa(rs.getInt("numero_mesa"));

                PedidoModel pedido = new PedidoModel();
                pedido.setID_Pedido(rs.getInt("ID_pedido"));
                pedido.setHoraPedido(rs.getTimestamp("hora_pedido").toLocalDateTime());
                pedido.setStatusPedido(rs.getBoolean("status_pedido"));
                pedido.setID_Mesa(mesa);

                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    public void atualizarPedido(PedidoModel pedido) throws SQLException {
        String sql = "UPDATE Pedido SET hora_pedido = ?, status_pedido = ?, Foreign_numero_mesa = ? WHERE ID_Pedido = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(pedido.getHoraPedido()));
            stmt.setBoolean(2, pedido.getStatusPedido());
            stmt.setInt(3, pedido.getID_Mesa().getNumeroMesa());
            stmt.setInt(4, pedido.getID_Pedido());

            stmt.executeUpdate();
        }
    }

    public void cancelarPedido(int idPedido) throws SQLException {
        String sql = "DELETE FROM Pedido WHERE ID_Pedido = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        }
    }
}
