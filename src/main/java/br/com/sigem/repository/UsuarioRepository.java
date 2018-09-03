package br.com.sigem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sigem.model.Usuario;
import br.com.sigem.repository.usuario.UsuarioRepositoryQuery;


/**
 * Classe responsável pelo acesso a dados dos usuários
 * @author Alan Lopes
 *
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery{
	
	/**
	 * Busca todos os usuários de acordo com o nome
	 * @param nome
	 * @return lista de usuários
	 */
	public List<Usuario> findByNome(String nome); 
	
	/**
	 * Busca todos os usuários de acordo com o email
	 * @param email
	 * @return
	 */
	public List<Usuario> findByEmail(String email);
	
	
	public List<Usuario> findByAtivo(int ativo);
	
}
