<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.pizzaria.model.ClienteModel" %>
<%@ page import="java.util.ArrayList" %>
<%
    @SuppressWarnings("unchecked")
    List<ClienteModel> listaClientes = (List<ClienteModel>) request.getAttribute("listaClientes");
    if (listaClientes == null) {
        listaClientes = new ArrayList<>();
    }

    String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Clientes</title>
    <link rel="stylesheet" type="text/css" href="visual.css" media="all"/>
</head>
<body>
    <h1>Lista de Clientes</h1>

    <% if (erro != null) { %>
        <div class="erro"><%= erro %></div>
    <% } %>

    <table>
        <tr>
            <th>CPF</th>
            <th>Nome</th>
            <th>Telefone</th>
            <th>Ações</th>
        </tr>
        <%
            if (!listaClientes.isEmpty()) {
                for (ClienteModel cliente : listaClientes) {
        %>
        <tr>
            <td><%= cliente.getCPF() %></td>
            <td><%= cliente.getNomeCliente() %></td>
            <td><%= cliente.getTelefone() %></td>
            <td>
         
                <form action="ClienteController" method="post">
                    <input type="hidden" name="action" value="deletar" />
                    <input type="hidden" name="cpf" value="<%= cliente.getCPF() %>" />
                    <button type="submit">Deletar</button>
                </form>


                <form action="ClienteController" method="post">
                    <input type="hidden" name="action" value="editar" />
                    <input type="hidden" name="cpfAntigo" value="<%= cliente.getCPF() %>" />
                    CPF: <input type="text" name="cpf" value="<%= cliente.getCPF() %>" />
                    Nome: <input type="text" name="nome" value="<%= cliente.getNomeCliente() %>" />
                    Telefone: <input type="text" name="telefone" value="<%= cliente.getTelefone() %>" />
                    <button type="submit">Editar</button>
                </form>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4">Nenhum cliente cadastrado.</td>
        </tr>
        <%
            }
        %>
    </table>

    <br/>
    <a href="index.jsp" class="voltar">Voltar ao Menu</a>
</body>
</html>
