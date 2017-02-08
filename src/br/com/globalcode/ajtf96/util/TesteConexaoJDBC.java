package br.com.globalcode.ajtf96.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexaoJDBC {
	
	private static final String NOME_BANCO = "aj";
	private static final String STR_CON = "jdbc:mysql://localhost:3306/" + NOME_BANCO;
	private static final String USER = "aj";
	private static final String PASSWORD = "ajtf96%";

	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection(STR_CON, USER, PASSWORD)) {
			System.out.println("Conexão realizada com sucesso");
		} catch (SQLException e) {
			System.out.println("ERRO! Falha ao obter conexão");
			e.printStackTrace();
		}
		
	}

}
