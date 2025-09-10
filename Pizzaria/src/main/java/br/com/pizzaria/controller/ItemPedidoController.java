package br.com.pizzaria.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import br.com.pizzaria.DAO.ItemCardapioDAO;
import br.com.pizzaria.DAO.ItemPedidoDAO;
import br.com.pizzaria.model.ItemCardapioModel;
import br.com.pizzaria.model.ItemPedidoModel;
import br.com.pizzaria.model.PedidoModel;
import br.com.pizzaria.utils.Conexao;

@WebServlet("/ItemPedidoController")
public class ItemPedidoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ItemPedidoDAO itemPedidoDAO;
    private ItemCardapioDAO itemCardapioDAO;

    public void init() throws ServletException {
        try {
            Connection conn = Conexao.getConnection();
            itemPedidoDAO = new ItemPedidoDAO(conn);
            itemCardapioDAO = new ItemCardapioDAO(conn);
        } catch (Exception e) {
            throw new ServletException("Erro ao inicializar DAOs", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            listarItensPedido(request, response);
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
                    adicionarItemPedido(request, response);
                    break;
                case "editar":
                    editarItemPedido(request, response);
                    break;
                case "deletar":
                    deletarItemPedido(request, response);
                    break;
                default:
                    listarItensPedido(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void adicionarItemPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int idPedido = Integer.parseInt(request.getParameter("idPedido"));
        int idCardapio = Integer.parseInt(request.getParameter("idCardapio"));
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));

      
        ItemCardapioModel cardapio = itemCardapioDAO.buscarPorId(idCardapio);
        if (cardapio == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item do cardápio não encontrado");
            return;
        }

        double precoUnitario = cardapio.getPreco();
        double precoTotal = quantidade * precoUnitario;

        PedidoModel pedido = new PedidoModel();
        pedido.setID_Pedido(idPedido);

        ItemPedidoModel item = new ItemPedidoModel(pedido, cardapio, quantidade, precoUnitario, precoTotal);
        itemPedidoDAO.criarItemPedido(item);

        response.sendRedirect("ItemPedidoController?action=listar");
    }

    private void editarItemPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int idItem = Integer.parseInt(request.getParameter("idItem"));
        int idPedido = Integer.parseInt(request.getParameter("idPedido"));
        int idCardapio = Integer.parseInt(request.getParameter("idCardapio"));
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));

        ItemCardapioModel cardapio = itemCardapioDAO.buscarPorId(idCardapio);
        if (cardapio == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item do cardápio não encontrado");
            return;
        }

        double precoUnitario = cardapio.getPreco();
        double precoTotal = quantidade * precoUnitario;

        PedidoModel pedido = new PedidoModel();
        pedido.setID_Pedido(idPedido);

        ItemPedidoModel item = new ItemPedidoModel(idItem, pedido, cardapio, quantidade, precoUnitario, precoTotal);
        itemPedidoDAO.alterarItemPedido(item);

        response.sendRedirect("ItemPedidoController?action=listar");
    }

    private void deletarItemPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int idItem = Integer.parseInt(request.getParameter("idItem"));
        itemPedidoDAO.excluirItemPedido(idItem);

        response.sendRedirect("ItemPedidoController?action=listar");
    }

    protected void listarItensPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
       
        List<Integer> mesasExistentes = itemPedidoDAO.listarMesasExistentes();
        request.setAttribute("mesasExistentes", mesasExistentes);


        String numeroMesaParam = request.getParameter("numeroMesa");
        List<ItemPedidoModel> itens;
        if (numeroMesaParam != null && !numeroMesaParam.isEmpty()) {
            itens = itemPedidoDAO.listarItensPorMesa(Integer.parseInt(numeroMesaParam));
        } else {
            itens = itemPedidoDAO.listarItensPedido();
        }

        request.setAttribute("listaItensPedido", itens);

        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/ItemPedidoPgs/listaItensPedido.jsp");
        dispatcher.forward(request, response);
    }

}

