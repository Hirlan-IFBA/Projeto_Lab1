<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.pizzaria.model.ItemPedidoModel" %>
<%@ page import="br.com.pizzaria.model.MesaModel" %>
<%@ page import="br.com.pizzaria.DAO.MesaDAO" %>
<%@ page import="br.com.pizzaria.utils.Conexao" %>

<%
    @SuppressWarnings("unchecked")
    List<ItemPedidoModel> listaItensPedido = (List<ItemPedidoModel>) request.getAttribute("listaItensPedido");
    if (listaItensPedido == null) {
        listaItensPedido = new java.util.ArrayList<>();
    }

    // Obtém as mesas existentes
    MesaDAO mesaDAO = new MesaDAO(Conexao.getConnection());
    List<MesaModel> mesasExistentes = mesaDAO.listarMesas();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Itens de Pedido</title>
    <link rel="stylesheet" type="text/css" href="visual.css" media="all"/>
</head>
<body>

<h2>Lista de Itens do Pedido</h2>

<form action="<%=request.getContextPath()%>/ItemPedidoController" method="get">
    <label class="desc">Filtrar por Mesa:</label>
    <select name="numeroMesa">
        <option value="">Todas</option>
        <% for (MesaModel mesa : mesasExistentes) { %>
            <option value="<%= mesa.getNumeroMesa() %>" 
                <%= request.getParameter("numeroMesa") != null &&
                    request.getParameter("numeroMesa").equals(String.valueOf(mesa.getNumeroMesa())) ? "selected" : "" %>>
                Mesa <%= mesa.getNumeroMesa() %>
            </option>
        <% } %>
    </select>
    <button type="submit">Filtrar</button>
</form>

<table border="1">
    <tr>
        <th>ID Item</th>
        <th>ID Pedido</th>
        <th>Mesa</th>
        <th>Hora Pedido</th>
        <th>Cardápio</th>
        <th>Quantidade</th>
        <th>Preço Unitário</th>
        <th>Preço Total</th>
        <th>Ações</th>
    </tr>
    <%
        for (ItemPedidoModel item : listaItensPedido) {
            if (request.getParameter("numeroMesa") != null && !request.getParameter("numeroMesa").isEmpty()) {
                int filtroMesa = Integer.parseInt(request.getParameter("numeroMesa"));
                if (item.getID_Pedido().getID_Mesa().getNumeroMesa() != filtroMesa) {
                    continue; // pula itens que não correspondem à mesa selecionada
                }
            }
    %>
    <tr>
        <td><%= item.getID_Item() %></td>
        <td><%= item.getID_Pedido() != null ? item.getID_Pedido().getID_Pedido() : "N/A" %></td>
        <td><%= item.getID_Pedido() != null && item.getID_Pedido().getID_Mesa() != null 
                     ? item.getID_Pedido().getID_Mesa().getNumeroMesa() : "N/A" %></td>
        <td><%= item.getID_Pedido() != null && item.getID_Pedido().getHoraPedido() != null
                     ? item.getID_Pedido().getHoraPedido() : "N/A" %></td>
        <td><%= item.getID_Cardapio() != null ? item.getID_Cardapio().getNome() : "N/A" %></td>
        <td><%= item.getQuantidade() %></td>
        <td><%= item.getPrecoUnitario() %></td>
        <td><%= item.getPrecoTotal() %></td>
        <td>
            <form action="<%=request.getContextPath()%>/ItemPedidoController" method="post" style="display:inline">
                <input type="hidden" name="action" value="deletar"/>
                <input type="hidden" name="idItem" value="<%= item.getID_Item() %>"/>
                <button type="submit">Deletar</button>
            </form>
        </td>
    </tr>
    <% } %>
    <% if (listaItensPedido.isEmpty()) { %>
    <tr>
        <td colspan="9">Nenhum item cadastrado.</td>
    </tr>
    <% } %>
</table>

<br/>
<a href="<%=request.getContextPath()%>/index.jsp" class="voltar">Voltar ao Menu</a>

</body>
</html>
