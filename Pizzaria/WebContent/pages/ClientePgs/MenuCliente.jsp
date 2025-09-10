<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Projeto Pizzaria - Cadastro de Cliente</title>
    <link rel="stylesheet" type="text/css" href="../../visual.css" media="all"/>
</head>
<body>

    <h2>Menu de Clientes</h2>

    <a href="<%= request.getContextPath() %>/ClienteController?action=listar" class="voltar">Listar Clientes</a><br><br>

    <form action="<%= request.getContextPath() %>/ClienteController" method="post" class="desc">
        <input type="hidden" name="action" value="adicionar" />
        CPF: <input type="text" name="cpf" required /><br><br>
        Nome: <input type="text" name="nome" required /><br><br>
        Telefone: <input type="text" name="telefone" /><br><br>
        <button type="submit">Adicionar Cliente</button>
    </form>

    <% 
        String erro = (String) request.getAttribute("erro"); 
        if (erro != null) { 
    %>
        <p style="color:red; font-weight:bold;"><%= erro %></p>
    <% } %>

</body>
</html>
