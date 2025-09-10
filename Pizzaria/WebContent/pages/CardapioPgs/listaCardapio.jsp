<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.pizzaria.model.ItemCardapioModel" %>
<%@ page import="java.util.ArrayList" %>
<%
    @SuppressWarnings("unchecked")
    List<ItemCardapioModel> listaItens = (List<ItemCardapioModel>) request.getAttribute("listaItens");
    if (listaItens == null) {
        listaItens = new ArrayList<>();
    }
%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="visual.css" media="all"/>
    <meta charset="UTF-8">
    <title>Lista de Itens</title>
</head>
<body>
    <h2>Adicionar Novo Item</h2>
    <form action="<%= request.getContextPath() %>/ItemCardapioController" method="post" class="desc">
        <input type="hidden" name="action" value="adicionar"/>

        Nome: <input type="text" name="nome" required /><br><br>
        Preço R$: <input type="number" name="preco" step="0.01" required /><br><br>
        Descrição: <input type="text" name="descricao" required /><br><br>

        <button type="submit">Salvar Item</button>
    </form>
    <hr>

    <h1>Lista de Itens</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Preço</th>
            <th>Descrição</th>
            <th>Ações</th>
        </tr>
        <%
            if (!listaItens.isEmpty()) {
                for (ItemCardapioModel item : listaItens) {
        %>
        <tr>
            <td><%= item.getID_Cardapio() %></td>
            <td><%= item.getNome() %></td>
            <td><%= item.getPreco() %></td>
            <td><%= item.getDescricao() %></td>
            <td>
                <!-- Deletar -->
                <form action="<%= request.getContextPath() %>/ItemCardapioController" method="post">
                    <input type="hidden" name="action" value="deletar" />
                    <input type="hidden" name="id" value="<%= item.getID_Cardapio() %>" />
                    <button type="submit">Deletar</button>
                </form>
                
                <!-- Editar -->
                 <form action="<%= request.getContextPath() %>/ItemCardapioController" method="post">
                    <input type="hidden" name="action" value="editar" />
                    <input type="hidden" name="id" value="<%= item.getID_Cardapio() %>" />
                    Nome: <input type="text" name="nome" value="<%= item.getNome() %>" />
                    Preço: <input type="number" step="0.01" name="preco" value="<%= item.getPreco() %>" />
                    Descrição: <input type="text" name="descricao" value="<%= item.getDescricao() %>" />
                    <button type="submit">Editar</button>
                </form>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5">Nenhum produto cadastrado.</td>
        </tr>
        <%
            }
        %>
    </table>

    <br/><div class="menu-botoes">
    <a href="index.jsp" class="voltar" >Voltar ao Menu</a></div>
</body>
</html>
