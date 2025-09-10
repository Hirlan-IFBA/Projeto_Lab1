package br.com.pizzaria.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import br.com.pizzaria.DAO.ItemCardapioDAO;
import br.com.pizzaria.model.ItemCardapioModel;
import br.com.pizzaria.utils.Conexao;

@WebServlet("/ItemCardapioController")
public class ItemCardapioController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ItemCardapioDAO itemDAO;

    public void init() throws ServletException {
        try {
            Connection conn = Conexao.getConnection();
            itemDAO = new ItemCardapioDAO(conn);
        } catch (Exception e) {
            throw new ServletException("Erro ao inicializar ItemCardapioDAO", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            listarItens(request, response);
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
                    adicionarItem(request, response);
                    break;
                case "editar":
                    editarItem(request, response);
                    break;
                case "deletar":
                    deletarItem(request, response);
                    break;
                default:
                    listarItens(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void adicionarItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String nome = request.getParameter("nome");
        double preco = Double.parseDouble(request.getParameter("preco"));
        String descricao = request.getParameter("descricao");

        // Não precisa do ID, ele será gerado pelo banco
        ItemCardapioModel item = new ItemCardapioModel(nome, preco, descricao);
        itemDAO.criarItem(item);

        response.sendRedirect("ItemCardapioController?action=listar");
    }

    private void editarItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        double preco = Double.parseDouble(request.getParameter("preco"));
        String descricao = request.getParameter("descricao");

        ItemCardapioModel item = new ItemCardapioModel(id, nome, preco, descricao);
        itemDAO.alterarItem(item);
        response.sendRedirect("ItemCardapioController?action=listar");
    }

    private void deletarItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        itemDAO.excluirItem(id);
        response.sendRedirect("ItemCardapioController?action=listar");
    }

    private void listarItens(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ItemCardapioModel> itens = itemDAO.listarItens();
        request.setAttribute("listaItens", itens);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/CardapioPgs/listaCardapio.jsp");
        dispatcher.forward(request, response);
    }
}
