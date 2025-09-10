package br.com.pizzaria.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import br.com.pizzaria.DAO.ItemCardapioDAO;
import br.com.pizzaria.DAO.ItemPedidoDAO;
import br.com.pizzaria.DAO.PedidoDAO;
import br.com.pizzaria.model.ItemCardapioModel;
import br.com.pizzaria.model.ItemPedidoModel;
import br.com.pizzaria.model.MesaModel;
import br.com.pizzaria.model.PedidoModel;
import br.com.pizzaria.utils.Conexao;

@WebServlet("/PedidoController")
public class PedidoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PedidoDAO pedidoDAO;
    private ItemPedidoDAO itemPedidoDAO;
    private ItemCardapioDAO itemCardapioDAO;

    public void init() throws ServletException {
        try {
            Connection conn = Conexao.getConnection();
            pedidoDAO = new PedidoDAO(conn);
            itemPedidoDAO = new ItemPedidoDAO(conn);
            itemCardapioDAO = new ItemCardapioDAO(conn);
        } catch (Exception e) {
            throw new ServletException("Erro ao inicializar DAOs", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            listarPedidos(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "novoPedidoComItens":
                    criarPedido(request, response);
                    break;
                case "deletarPedido":
                    deletarPedido(request, response);
                    break;
                default:
                    listarPedidos(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void criarPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int numeroMesa = Integer.parseInt(request.getParameter("numeroMesa"));

        // 1️⃣ Cria o pedido
        PedidoModel pedido = new PedidoModel();
        MesaModel mesa = new MesaModel();
        mesa.setNumeroMesa(numeroMesa);
        pedido.setID_Mesa(mesa);

        // 2️⃣ Inicializa a hora e o status do pedido
        pedido.setHoraPedido(java.time.LocalDateTime.now()); // evita NullPointerException
        pedido.setStatusPedido(false); // false = em preparo

        // 3️⃣ Salva no banco
        pedidoDAO.criarPedido(pedido); 

        // 4️⃣ Cria itens do pedido se houver
        String[] idsCardapio = request.getParameterValues("idCardapio");
        String[] quantidades = request.getParameterValues("quantidade");

        if (idsCardapio != null && quantidades != null) {
            for (int i = 0; i < idsCardapio.length; i++) {
                ItemCardapioModel itemCardapio = itemCardapioDAO.buscarPorId(Integer.parseInt(idsCardapio[i]));
                int quantidade = Integer.parseInt(quantidades[i]);
                double precoUnitario = itemCardapio.getPreco();
                double precoTotal = precoUnitario * quantidade;

                ItemPedidoModel itemPedido = new ItemPedidoModel();
                itemPedido.setID_Pedido(pedido);
                itemPedido.setID_Cardapio(itemCardapio);
                itemPedido.setQuantidade(quantidade);
                itemPedido.setPrecoUnitario(precoUnitario);
                itemPedido.setPrecoTotal(precoTotal);

                itemPedidoDAO.criarItemPedido(itemPedido);
            }
        }

        response.sendRedirect("PedidoController?action=listar");
    }

    private void deletarPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int idPedido = Integer.parseInt(request.getParameter("idPedido"));
        pedidoDAO.cancelarPedido(idPedido);
        response.sendRedirect("PedidoController?action=listar");
    }

    private void listarPedidos(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<PedidoModel> pedidos = pedidoDAO.listarPedidos();
        request.setAttribute("listaPedidos", pedidos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/PedidoPgs/listaPedido.jsp");
        dispatcher.forward(request, response);
    }
}
