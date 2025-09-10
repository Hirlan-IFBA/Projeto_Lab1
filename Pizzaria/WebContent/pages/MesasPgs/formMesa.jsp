<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.pizzaria.model.MesaModel" %>
<%@ page import="br.com.pizzaria.model.ClienteModel" %>
<%@ page import="br.com.pizzaria.DAO.ClienteDAO" %>
<%@ page import="br.com.pizzaria.utils.Conexao" %>

<%
    MesaModel mesa = (MesaModel) request.getAttribute("mesa");

    // Buscar todos os clientes cadastrados
    ClienteDAO clienteDAO = new ClienteDAO(Conexao.getConnection());
    List<ClienteModel> clientes = clienteDAO.listarClientes();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= (mesa != null) ? "Editar Mesa" : "Adicionar Mesa" %></title>
    <link rel="stylesheet" type="text/css" href="../../visual.css" media="all"/>
</head>
<body>
    <h1><%= (mesa != null) ? "Editar Mesa" : "Adicionar Mesa" %></h1>
    
    <form action="<%= request.getContextPath() %>/MesaController" method="post" class="desc">
        <input type="hidden" name="action" value="<%= (mesa != null) ? "editar" : "adicionar" %>" />
        
        <% if (mesa != null) { %>
            Número da Mesa: 
            <input type="text" value="<%= mesa.getNumeroMesa() %>" readonly /><br/><br/>
            <input type="hidden" name="numeroMesa" value="<%= mesa.getNumeroMesa() %>"/>
        <% } %>
        
        Status: 
        <select name="status">
            <option value="true" <%= (mesa != null && mesa.getStatus()) ? "selected" : "" %>>Ocupada</option>
            <option value="false" <%= (mesa != null && (mesa.getStatus() == null || !mesa.getStatus())) ? "selected" : "" %>>Livre</option>
        </select><br/><br/>
        
        CPF do Cliente: 
<select name="cpfCliente">
    <option value="">-- Sem Cliente --</option>
    <% for (ClienteModel c : clientes) { %>
        <option value="<%= c.getCPF() %>"
            <%= (mesa != null && mesa.getID_Cliente() != null 
                && c.getCPF().equals(mesa.getID_Cliente().getCPF())) ? "selected" : "" %>>
            <%= c.getCPF() %> - <%= c.getNomeCliente() %>
        </option>
    <% } %>
</select><br/><br/>
        
        <button type="submit"><%= (mesa != null) ? "Editar" : "Adicionar" %></button>
    </form>

    <br/><br/><br/>
    <a href="/Pizzaria/MesaController?action=listar" class="voltar">Voltar à Lista</a>
</body>
</html>
