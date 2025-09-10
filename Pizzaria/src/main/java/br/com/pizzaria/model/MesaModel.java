package br.com.pizzaria.model;

public class MesaModel {
		
	private int NumeroMesa;
	private Boolean Status;
	private ClienteModel ID_Cliente;
	
	public MesaModel() {
	}
	
	public MesaModel(int NumeroMesa,Boolean Status, ClienteModel ID_Cliente) {
		this.NumeroMesa = NumeroMesa;
		this.Status = Status;
		this.ID_Cliente =ID_Cliente;
	}
	public MesaModel(Boolean Status, ClienteModel ID_Cliente) {
		this.Status = Status;
		this.ID_Cliente =ID_Cliente;
	}

	public int getNumeroMesa() {
		return NumeroMesa;
	}

	public void setNumeroMesa(int numeroMesa) {
		NumeroMesa = numeroMesa;
	}

	public Boolean getStatus() {
		return Status;
	}

	public void setStatus(Boolean status) {
		Status = status;
	}

	public ClienteModel getID_Cliente() {
		return ID_Cliente;
	}

	public void setID_Cliente(ClienteModel iD_Cliente) {
		ID_Cliente = iD_Cliente;
	}
	
}
