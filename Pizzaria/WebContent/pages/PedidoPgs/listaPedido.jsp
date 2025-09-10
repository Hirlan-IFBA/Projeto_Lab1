<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.pizzaria.model.ItemCardapioModel" %>
<%@ page import="br.com.pizzaria.DAO.ItemCardapioDAO" %>
<%@ page import="br.com.pizzaria.model.MesaModel" %>
<%@ page import="br.com.pizzaria.DAO.MesaDAO" %>
<%@ page import="br.com.pizzaria.utils.Conexao" %>
<%
    // Lista de itens do cardápio
    ItemCardapioDAO cardapioDAO = new ItemCardapioDAO(Conexao.getConnection());
    List<ItemCardapioModel> listaCardapio = cardapioDAO.listarItens();

    // Lista de mesas já cadastradas
    MesaDAO mesaDAO = new MesaDAO(Conexao.getConnection());
    List<MesaModel> listaMesas = mesaDAO.listarMesas();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Novo Pedido</title>
    <link rel="stylesheet" type="text/css" href="visual.css" media="all"/>
    <script>
        function adicionarItem() {
            const tabela = document.getElementById("itensTabela");
            const novaLinha = tabela.insertRow(-1);

            const cel1 = novaLinha.insertCell(0);
            const cel2 = novaLinha.insertCell(1);
            const cel3 = novaLinha.insertCell(2);
            const cel4 = novaLinha.insertCell(3);

            cel1.innerHTML = `
                <select name="idCardapio" onchange="atualizarPreco(this)">
                    <option value="">Selecione</option>
                    <% for(ItemCardapioModel item : listaCardapio){ %>
                        <option value="<%= item.getID_Cardapio() %>" data-preco="<%= item.getPreco() %>">
                            <%= item.getNome() %>
                        </option>
                    <% } %>
                </select>`;
            cel2.innerHTML = `<input type="number" name="quantidade" value="1" min="1" onchange="calcularTotal(this)">`;
            cel3.innerHTML = `<input type="number" name="precoUnitario" step="0.01" readonly>`;
            cel4.innerHTML = `<input type="number" name="precoTotal" step="0.01" readonly>`;

            calcularTotal(cel2.firstChild);
        }

        function atualizarPreco(select) {
            const precoUnitario = select.options[select.selectedIndex].dataset.preco;
            const linha = select.parentElement.parentElement;
            linha.cells[2].children[0].value = precoUnitario;
            calcularTotal(linha.cells[1].children[0]);
        }

        function calcularTotal(inputQtd) {
            const linha = inputQtd.parentElement.parentElement;
            const precoUnitario = parseFloat(linha.cells[2].children[0].value) || 0;
            const quantidade = parseInt(inputQtd.value) || 0;
            linha.cells[3].children[0].value = (precoUnitario * quantidade).toFixed(2);
        }
    </script>
</head>
<body>
    <h2>Novo Pedido</h2>
    <form action="<%=request.getContextPath()%>/PedidoController" method="post" class="desc">
        <input type="hidden" name="action" value="novoPedidoComItens">

        <!-- Select de mesas existentes -->
        Número da Mesa: 
        <select name="numeroMesa" required>
            <option value="">Selecione</option>
            <% for(MesaModel mesa : listaMesas) { %>
                <option value="<%= mesa.getNumeroMesa() %>"><%= mesa.getNumeroMesa() %></option>
            <% } %>
        </select>
        <br><br>

        <h3 class="viv">Itens do Pedido:</h3>
        <table border="1" id="itensTabela">
            <tr>
                <th>Item do Cardápio</th>
                <th>Quantidade</th>
                <th>Preço Unitário</th>
                <th>Preço Total</th>
            </tr>
        </table>
        <button type="button" onclick="adicionarItem()">Adicionar Item</button>
        <br><br>
        <button type="submit">Salvar Pedido</button>
    </form>

    <br><br><br>
    <a href="<%=request.getContextPath()%>/index.jsp" class="voltar">Voltar ao Menu</a>
</body>
</html>
