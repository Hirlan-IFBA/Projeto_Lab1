package br.com.pizzaria.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

import br.com.pizzaria.DAO.ClienteDAO;
import br.com.pizzaria.model.ClienteModel;
import br.com.pizzaria.utils.Conexao;

@WebServlet("/ClienteController")
public class ClienteController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;

    public void init() throws ServletException {
        try {
            Connection conn = Conexao.getConnection();
            clienteDAO = new ClienteDAO(conn);
        } catch (Exception e) {
            throw new ServletException("Erro ao inicializar ClienteDAO", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            listarClientes(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "adicionar":
                    novoCliente(request, response);
                    break;
                case "editar":
                    editarCliente(request, response);
                    break;
                case "deletar":
                    deletarCliente(request, response);
                    break;
                case "listar":
                default:
                    listarClientes(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void novoCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");

        try {
            ClienteModel cliente = new ClienteModel(cpf, nome, telefone);
            clienteDAO.criarCliente(cliente);
            response.sendRedirect("ClienteController?action=listar");
        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cpfAntigo = request.getParameter("cpfAntigo");
        String cpfNovo = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");

        
        if (!cpfAntigo.equals(cpfNovo) && clienteDAO.clienteExiste(cpfNovo)) {
           
            request.setAttribute("erro", "Erro: CPF já cadastrado!");
            listarClientes(request, response);
            return;
        }

      
        ClienteModel cliente = new ClienteModel(cpfNovo, nome, telefone);
        clienteDAO.alterarClienteComCpf(cliente , cpfAntigo); // método que permite alterar CPF
        response.sendRedirect("ClienteController?action=listar");
    }


    private void deletarCliente(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cpf = request.getParameter("cpf");
        clienteDAO.excluirCliente(cpf);
        response.sendRedirect("ClienteController?action=listar");
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ClienteModel> listaClientes = clienteDAO.listarClientes();
        request.setAttribute("listaClientes", listaClientes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/ClientePgs/listaClientes.jsp");
        dispatcher.forward(request, response);
    }
}

