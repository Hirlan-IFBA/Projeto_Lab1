package br.com.pizzaria.model;

import java.time.LocalDateTime;

public class PedidoModel {

		private int ID_Pedido;
		private LocalDateTime HoraPedido;
		private Boolean StatusPedido;
		private MesaModel ID_Mesa;
		
		public PedidoModel() {	
		}
		
		public PedidoModel(int ID_Pedido,LocalDateTime HoraPedido,Boolean StatusPedido, MesaModel ID_Mesa) {
			this.ID_Pedido = ID_Pedido;
			this.HoraPedido = HoraPedido;
			this.StatusPedido = StatusPedido;
			this.ID_Mesa = ID_Mesa;			
		}
		
		public PedidoModel(LocalDateTime HoraPedido,Boolean StatusPedido, MesaModel ID_Mesa) {
			this.HoraPedido = HoraPedido;
			this.StatusPedido = StatusPedido;
			this.ID_Mesa = ID_Mesa;	
		}
		
		
		public int getID_Pedido() {
			return ID_Pedido;
		}

		public void setID_Pedido(int iD_Pedido) {
			ID_Pedido = iD_Pedido;
		}

		public LocalDateTime getHoraPedido() {
			return HoraPedido;
		}

		public void setHoraPedido(LocalDateTime horaPedido) {
			HoraPedido = horaPedido;
		}

		public Boolean getStatusPedido() {
			return StatusPedido;
		}

		public void setStatusPedido(Boolean statusPedido) {
			StatusPedido = statusPedido;
		}

		public MesaModel getID_Mesa() {
			return ID_Mesa;
		}

		public void setID_Mesa(MesaModel iD_Mesa) {
			ID_Mesa = iD_Mesa;
		}
		
		
}
