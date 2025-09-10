<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="br.com.pizzaria.model.MesaModel" %>
<%@ page import="br.com.pizzaria.model.ClienteModel" %>
<%
    @SuppressWarnings("unchecked")
    List<MesaModel> listaMesas = (List<MesaModel>) request.getAttribute("listaMesas");
    if (listaMesas == null) {
        listaMesas = new ArrayList<>();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Mesas</title>
    <link rel="stylesheet" type="text/css" href="visual.css" media="all"/>
</head>
<body>


    <h1>Lista de Mesas</h1>

    <a href="pages/MesasPgs/formMesa.jsp" class="voltar">Adicionar Nova Mesa</a>
    <br/><br/>

    <table>
        <tr>
            <th>Número</th>
            <th>Status</th>
            <th>CPF do Cliente</th>
            <th>Ações</th>
        </tr>
        <%
            if (!listaMesas.isEmpty()) {
                for (MesaModel mesa : listaMesas) {
                    ClienteModel c = mesa.getID_Cliente();
        %>
        <tr>
            <td><%= mesa.getNumeroMesa() %></td>
            <td><%= (mesa.getStatus() != null && mesa.getStatus()) ? "Ocupada" : "Livre" %></td>
            <td><%= (c != null && c.getCPF() != null) ? c.getCPF() : "" %></td>
            <td>
               
                <form action="MesaController" method="get">
                    <input type="hidden" name="action" value="editarForm" />
                    <input type="hidden" name="numeroMesa" value="<%= mesa.getNumeroMesa() %>" />
                    <button type="submit">Editar</button>
                </form>

            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4">Nenhuma mesa cadastrada.</td>
        </tr>
        <%
            }
        %>
    </table>

    <br/>
    <a href="/Pizzaria/" class="voltar">Voltar ao Menu</a>
</body>
</html>
