package br.com.pizzaria.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.pizzaria.model.ClienteModel;

public class ClienteDAO {

    private Connection conexao;

    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean clienteExiste(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM clientes WHERE CPF = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void criarCliente(ClienteModel cliente) throws SQLException {
        if (clienteExiste(cliente.getCPF())) {
            throw new SQLException("CPF já cadastrado!");
        }

        String sql = "INSERT INTO clientes (CPF, NomeCliente, Telefone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCPF());
            stmt.setString(2, cliente.getNomeCliente());
            stmt.setString(3, cliente.getTelefone());
            stmt.executeUpdate();
        }
    }

    public List<ClienteModel> listarClientes() throws SQLException {
        List<ClienteModel> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ClienteModel cliente = new ClienteModel();
                cliente.setCPF(rs.getString("CPF"));
                cliente.setNomeCliente(rs.getString("NomeCliente"));
                cliente.setTelefone(rs.getString("Telefone"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public void alterarClienteComCpf(ClienteModel cliente, String cpfAntigo) throws SQLException {
        if (!cliente.getCPF().equals(cpfAntigo) && clienteExiste(cliente.getCPF())) {
            throw new SQLException("CPF já cadastrado!");
        }

        String sql = "UPDATE clientes SET NomeCliente = ?, Telefone = ?, CPF = ? WHERE CPF = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getCPF());
            stmt.setString(4, cpfAntigo);
            stmt.executeUpdate();
        }
    }
    
    public void alterarClienteComCPF(String cpfAntigo, ClienteModel cliente) throws SQLException {
        String sql = "UPDATE clientes SET CPF = ?, NomeCliente = ?, Telefone = ? WHERE CPF = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCPF());
            stmt.setString(2, cliente.getNomeCliente());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cpfAntigo);
            stmt.executeUpdate();
        }
    }


    public void excluirCliente(String cpf) throws SQLException {
        String sql = "DELETE FROM clientes WHERE CPF = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }
}
