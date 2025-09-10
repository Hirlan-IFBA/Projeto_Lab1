package br.com.pizzaria.model;

public class ClienteModel {
		
	private String CPF;
	private String NomeCliente;
	private String Telefone;
	
	public ClienteModel() {	
	}
	
	public ClienteModel(String CPF,String NomeCliente, String Telefone) {
	this.CPF = CPF;
	this.NomeCliente = NomeCliente;
	this.Telefone = Telefone;
	
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getTelefone() {
		return Telefone;
	}

	public void setTelefone(String telefone) {
		Telefone = telefone;
	}

	public String getNomeCliente() {
		return NomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		NomeCliente = nomeCliente;
	}
		
	
}
