package br.com.sigem.util;

public class JdbcUtils {
	
	public static final String USUARIO_POR_LOGIN = "SELECT nome_usuario, senha, ativo, nome FROM usuario"
			+ " WHERE nome_usuario = ? AND ativo = 1";
	
	public static final String PERMISSOES_POR_USUARIO = "SELECT u.nome_usuario, p.nome as nome_permissao FROM usuario_permissao up"
			+ " JOIN usuario u ON u.id = up.usuario_id"
			+ " JOIN permissao p ON p.id = up.permissao_id"
			+ " WHERE u.nome_usuario = ? AND u.ativo = 1";
	
	public static final String PERMISSOES_POR_GRUPO = "SELECT g.id, g.nome_usuario, p.nome  as nome_permissao FROM grupo_permissao gp"
			+ " JOIN grupo g ON g.id = gp.grupo_id"
			+ " JOIN permissao p ON p.id = gp.permissao_id"
			+ " JOIN usuario_grupo ug ON ug.grupo_id = g.id"
			+ " JOIN usuario u ON u.id = ug.usuario_id"
			+ " WHERE u.nome_usuario = ?";

}
