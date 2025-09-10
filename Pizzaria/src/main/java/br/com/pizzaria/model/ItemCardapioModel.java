package br.com.pizzaria.model;

public class ItemCardapioModel {

	private int ID_Cardapio;
	private String Nome;
	private double Preco;
	private String Descricao;
	
	public ItemCardapioModel() {
	}
	
	public ItemCardapioModel(int ID_Cardapio, String Nome, double Preco, String Descricao) {
	this.ID_Cardapio = ID_Cardapio;
	this.Nome = Nome;
	this.Preco = Preco;
	this.Descricao = Descricao;
}

	public ItemCardapioModel(String Nome, double Preco, String Descricao) {
		this.Nome = Nome;
		this.Preco = Preco;
		this.Descricao = Descricao;
	}

	public int getID_Cardapio() {
		return ID_Cardapio;
	}

	public void setID_Cardapio(int iD_Cardapio) {
		ID_Cardapio = iD_Cardapio;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public double getPreco() {
		return Preco;
	}

	public void setPreco(double preco) {
		Preco = preco;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}
	
}