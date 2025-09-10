package br.com.pizzaria.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {

	    private static final String URL = "jdbc:mysql://localhost:3306/No_Ponto";
	    private static final String USUARIO = "root";
	    private static final String SENHA = "123456";

	    public static Connection getConnection() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            return DriverManager.getConnection(URL, USUARIO, SENHA);
	        } catch (ClassNotFoundException | SQLException e) {
	            throw new RuntimeException("Erro na conexão com o banco de dados", e);
	        }
	    }

	    /* para testar a conexão após instalar o Apache na maquina e ter criado uma new server no Eclipse, sem ter implementado código ainda,
	    basta inicar o TomCat no Eclipse e tirar o comentário abaixo e rodar a classe.
	    Daí, estará confirmando se a conexão com o banco foi realizada. Só não esquece de iniciar o serviço do MySQL no services do Windows.
	   */
	    public static void main(String[] args) {
	        try {
	            Connection conexao = getConnection();
	            if (conexao != null) {
	                System.out.println("Conexão bem-sucedida!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
	

