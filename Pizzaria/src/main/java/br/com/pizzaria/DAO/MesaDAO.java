package br.com.pizzaria.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.pizzaria.model.ClienteModel;
import br.com.pizzaria.model.MesaModel;

public class MesaDAO {

    private Connection conexao;

    public MesaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Adiciona nova mesa
    public void criarMesa(MesaModel mesa) throws SQLException {
        String sql = "INSERT INTO Mesa (status_mesa, Foreign_CPF_cliente) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setBoolean(1, mesa.getStatus());

            if (mesa.getID_Cliente() != null) {
                stmt.setString(2, mesa.getID_Cliente().getCPF());
            } else {
                stmt.setNull(2, Types.VARCHAR);
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    mesa.setNumeroMesa(rs.getInt(1));
                }
            }
        }
    }

    // Lista todas as mesas
    public List<MesaModel> listarMesas() throws SQLException {
        List<MesaModel> mesas = new ArrayList<>();
        String sql = "SELECT numero_mesa, status_mesa, Foreign_CPF_cliente FROM Mesa";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MesaModel mesa = new MesaModel();
                mesa.setNumeroMesa(rs.getInt("numero_mesa"));
                mesa.setStatus(rs.getBoolean("status_mesa"));

                String cpfCliente = rs.getString("Foreign_CPF_cliente");
                if (cpfCliente != null && !cpfCliente.isEmpty()) {
                    ClienteModel cliente = new ClienteModel();
                    cliente.setCPF(cpfCliente);
                    mesa.setID_Cliente(cliente);
                } else {
                    mesa.setID_Cliente(null);
                }

                mesas.add(mesa);
            }
        }
        return mesas;
    }

    // Busca mesa por número
    public MesaModel buscarMesa(int numeroMesa) throws SQLException {
        String sql = "SELECT numero_mesa, status_mesa, Foreign_CPF_cliente FROM Mesa WHERE numero_mesa = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, numeroMesa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MesaModel mesa = new MesaModel();
                    mesa.setNumeroMesa(rs.getInt("numero_mesa"));
                    mesa.setStatus(rs.getBoolean("status_mesa"));

                    String cpfCliente = rs.getString("Foreign_CPF_cliente");
                    if (cpfCliente != null && !cpfCliente.isEmpty()) {
                        ClienteModel cliente = new ClienteModel();
                        cliente.setCPF(cpfCliente);
                        mesa.setID_Cliente(cliente);
                    } else {
                        mesa.setID_Cliente(null);
                    }

                    return mesa;
                }
            }
        }
        return null;
    }

    // Edita mesa existente
    public void alterarMesa(MesaModel mesa) throws SQLException {
        String sql = "UPDATE Mesa SET status_mesa = ?, Foreign_CPF_cliente = ? WHERE numero_mesa = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setBoolean(1, mesa.getStatus());

            if (mesa.getID_Cliente() != null) {
                stmt.setString(2, mesa.getID_Cliente().getCPF());
            } else {
                stmt.setNull(2, Types.VARCHAR);
            }

            stmt.setInt(3, mesa.getNumeroMesa());
            stmt.executeUpdate();
        }
    }

    // Deleta mesa pelo número
    public void excluirMesa(int numeroMesa) throws SQLException {
        String sql = "DELETE FROM Mesa WHERE numero_mesa = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, numeroMesa);
            stmt.executeUpdate();
        }
    }
}
