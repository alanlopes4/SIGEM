package br.com.sigem.util;

import static br.com.sigem.util.JdbcUtils.USUARIO_POR_LOGIN;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.sigem.security.Conexao;

/**
 * Classe reposnsável por inserir os valores padrões no banco de dados sigem
 * @author Alan
 *
 */
public class InitDatabase {

	public static final int ROLE_USUARIOS = 1;
	public static final int ROLE_PRODUTOS = 2;
	public static final int ROLE_RELATORIOS = 3;
	public static final int ROLE_RELATORIOS_HISTORICOPRECO = 4;
	public static final int ROLE_RELATORIOS_ENTRADAPRODUTO = 5;
	public static final int ROLE_RELATORIOS_ESTOQUEDISPONIVEL = 6;
	public static final int ROLE_USUARIOS_CONSULTAR = 7;
	public static final int ROLE_USUARIOS_CADASTRAR = 8;
	public static final int ROLE_USUARIOS_EDITAR = 9;
	public static final int ROLE_USUARIOS_EXCLUIR = 10;
	public static final int ROLE_PRODUTOS_CONSULTAR = 11;
	public static final int ROLE_PRODUTOS_CADASTRAR = 12;
	public static final int ROLE_PRODUTOS_EDITAR = 13;
	public static final int ROLE_PRODUTOS_EXCLUIR = 14;
	public static final int ROLE_PRODUTOS_BAIXA = 15;
	public static final int ROLE_PERFIL = 16;
	public static final int ROLE_PERFIL_CONSULTAR = 17;
	public static final int ROLE_PERFIL_CADASTRAR = 18;
	public static final int ROLE_PERFIL_EDITAR = 19;
	public static final int ROLE_PERFIL_EXCLUIR = 20;
	public static final int ROLE_ESTOQUE = 21;
	
	private static ArrayList<String> permissoes = new ArrayList<String>() {
		{
			add("ROLE_USUARIOS");
			add("ROLE_PRODUTOS");
			add("ROLE_RELATORIOS");
			add("ROLE_RELATORIOS_HISTORICOPRECO");
			add("ROLE_RELATORIOS_ENTRADAPRODUTO");
			add("ROLE_RELATORIOS_ESTOQUEDISPONIVEL");
			add("ROLE_USUARIOS_CONSULTAR");
			add("ROLE_USUARIOS_CADASTRAR");
			add("ROLE_USUARIOS_EDITAR");
			add("ROLE_USUARIOS_EXCLUIR");
			add("ROLE_PRODUTOS_CONSULTAR");
			add("ROLE_PRODUTOS_CADASTRAR");
			add("ROLE_PRODUTOS_EDITAR");
			add("ROLE_PRODUTOS_EXCLUIR");
			add("ROLE_PRODUTOS_BAIXA");
			add("ROLE_PERFIL");
			add("ROLE_PERFIL_CONSULTAR");
			add("ROLE_PERFIL_CADASTRAR");
			add("ROLE_PERFIL_EDITAR");
			add("ROLE_PERFIL_EXCLUIR");
			add("ROLE_ESTOQUE");
		}
	};
	
	public static final String INSERIR_PERMISSOES = "INSERT INTO permissao (nome) VALUES (?)";
	
	public static void main(String[] args) {
		
		Connection connection = Conexao.getConnection();
		PreparedStatement ps;
		try {
			for(String permissao : permissoes) {
				ps = connection.prepareStatement(INSERIR_PERMISSOES);
				ps.setString(1, permissao);
				ps.executeUpdate();
			}
			
			System.out.println("Permissoes cadastradas com sucesso!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
