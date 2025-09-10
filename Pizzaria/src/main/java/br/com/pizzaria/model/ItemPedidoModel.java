package br.com.pizzaria.model;

public class ItemPedidoModel {
		
		private int ID_Item;
		private PedidoModel ID_Pedido;
		private ItemCardapioModel ID_Cardapio;
		private int Quantidade;
		private double PrecoUnitario;
		private double PrecoTotal;
		
		public ItemPedidoModel() {
		}
		
		public ItemPedidoModel(int ID_Item, PedidoModel ID_Pedido, ItemCardapioModel ID_Cardapio,int Quantidade, double PrecoUnitario, double PrecoTotal) {
			this.ID_Item = ID_Item;
			this.ID_Pedido = ID_Pedido;
			this.ID_Cardapio = ID_Cardapio;
			this.Quantidade = Quantidade;
			this.PrecoUnitario = PrecoUnitario;
			this.PrecoTotal = PrecoTotal;
		}
		
		public ItemPedidoModel(PedidoModel ID_Pedido, ItemCardapioModel ID_Cardapio,int Quantidade, double PrecoUnitario, double PrecoTotal) {
			this.ID_Pedido = ID_Pedido;
			this.ID_Cardapio = ID_Cardapio;
			this.Quantidade = Quantidade;
			this.PrecoUnitario = PrecoUnitario;
			this.PrecoTotal = PrecoTotal;
		}

		public int getID_Item() {
			return ID_Item;
		}

		public void setID_Item(int iD_Item) {
			ID_Item = iD_Item;
		}

		public PedidoModel getID_Pedido() {
			return ID_Pedido;
		}

		public void setID_Pedido(PedidoModel iD_Pedido) {
			ID_Pedido = iD_Pedido;
		}

		public ItemCardapioModel getID_Cardapio() {
			return ID_Cardapio;
		}

		public void setID_Cardapio(ItemCardapioModel iD_Cardapio) {
			ID_Cardapio = iD_Cardapio;
		}

		public int getQuantidade() {
			return Quantidade;
		}

		public void setQuantidade(int quantidade) {
			Quantidade = quantidade;
		}

		public double getPrecoUnitario() {
			return PrecoUnitario;
		}

		public void setPrecoUnitario(double precoUnitario) {
			PrecoUnitario = precoUnitario;
		}

		public double getPrecoTotal() {
			return PrecoTotal;
		}

		public void setPrecoTotal(double precoTotal) {
			PrecoTotal = precoTotal;
		}
		
}
