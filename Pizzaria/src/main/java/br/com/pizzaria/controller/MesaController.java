package br.com.pizzaria.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

import br.com.pizzaria.DAO.MesaDAO;
import br.com.pizzaria.model.MesaModel;
import br.com.pizzaria.model.ClienteModel;
import br.com.pizzaria.utils.Conexao;

@WebServlet("/MesaController")
public class MesaController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MesaDAO mesaDAO;

    public void init() throws ServletException {
        try {
            Connection conn = Conexao.getConnection();
            mesaDAO = new MesaDAO(conn);
        } catch (Exception e) {
            throw new ServletException("Erro ao inicializar MesaDAO", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("editarForm".equals(action)) {
                mostrarFormularioEdicao(request, response);
            } else {
                listarMesas(request, response);
            }
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
                    adicionarMesa(request, response);
                    break;
                case "editar":
                    editarMesa(request, response);
                    break;
                case "deletar":
                    deletarMesa(request, response);
                    break;
                default:
                    listarMesas(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listarMesas(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<MesaModel> listaMesas = mesaDAO.listarMesas();
        request.setAttribute("listaMesas", listaMesas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/MesasPgs/listaMesa.jsp");
        dispatcher.forward(request, response);
    }

    private void adicionarMesa(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        String cpfCliente = request.getParameter("cpfCliente");

        ClienteModel cliente = (cpfCliente != null && !cpfCliente.isEmpty()) ? new ClienteModel() : null;
        if (cliente != null) cliente.setCPF(cpfCliente);

        MesaModel mesa = new MesaModel(0, status, cliente);
        mesaDAO.criarMesa(mesa);

        response.sendRedirect("MesaController?action=listar");
    }

    private void editarMesa(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int numeroMesa = Integer.parseInt(request.getParameter("numeroMesa"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        String cpfCliente = request.getParameter("cpfCliente");

        ClienteModel cliente = (cpfCliente != null && !cpfCliente.isEmpty()) ? new ClienteModel() : null;
        if (cliente != null) cliente.setCPF(cpfCliente);

        MesaModel mesa = new MesaModel(numeroMesa, status, cliente);
        mesaDAO.alterarMesa(mesa);

        response.sendRedirect("MesaController?action=listar");
    }

    private void deletarMesa(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int numeroMesa = Integer.parseInt(request.getParameter("numeroMesa"));
        mesaDAO.excluirMesa(numeroMesa);
        response.sendRedirect("MesaController?action=listar");
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int numeroMesa = Integer.parseInt(request.getParameter("numeroMesa"));
        MesaModel mesa = mesaDAO.buscarMesa(numeroMesa);
        request.setAttribute("mesa", mesa);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/MesasPgs/formMesa.jsp");
        dispatcher.forward(request, response);
    }
}

