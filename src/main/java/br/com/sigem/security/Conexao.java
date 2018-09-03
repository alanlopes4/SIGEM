package br.com.sigem.security;

import java.sql.SQLException;


import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.Connection;
/**
 * Classe responsável pela conexão com o banco de dados
 * Disponibiliza somente uma instancia para a conexão
 * @author Alan
 * CLASSE SINGLETON
 */
@Component
public class Conexao {
	
private static Connection connection;
    
    private static String dsn = "jdbc:mysql://localhost:3306/sigem?useSSL=false&zeroDateTimeBehavior=convertToNull";
    private static String username = "wbhum";
    private static String password = "wbhum";
    
    
    public static synchronized Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(dsn, username, password);
            } catch (SQLException ex) {
                System.out.println("Houve um erro ao conectar com o Banco de Dados.");
            }
        }
        
        return connection;
    }
}
