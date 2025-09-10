<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Projeto Pizzaria</title>
    <link rel="stylesheet" type="text/css" href="visual.css" media="all"/>
</head>
<body>
    <div class="cabecalho">
        <h1 class="menu">Projeto Pizzaria</h1>
        <div class="logo">
            <img src="logoPizza.png" alt="logo"  width="90" height="90">
        </div>
    </div>

    <h2 class="menu">Menu Principal</h2>
    <div class="menu-botoes">
        <a href="<%=request.getContextPath()%>/pages/ClientePgs/MenuCliente.jsp" class="pags">Clientes</a>
        <a href="<%=request.getContextPath()%>/MesaController?action=listar" class="pags">Mesas</a>
        <a href="<%=request.getContextPath()%>/PedidoController?action=listar" class="pags">Pedidos</a>
        <a href="<%=request.getContextPath()%>/ItemCardapioController?action=listar" class="pags">Cardápio</a>
        <a href="<%=request.getContextPath()%>/ItemPedidoController?action=listar" class="pags">Itens de Pedido</a>
    </div>
</body>
</html>
