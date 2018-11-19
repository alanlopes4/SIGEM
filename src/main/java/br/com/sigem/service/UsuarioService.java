package br.com.sigem.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigem.model.Usuario;
import br.com.sigem.repository.UsuarioRepository;
import br.com.sigem.repository.filter.UsuarioFilter;
/**
 * 
 * @author Alan Lopes
 *
 */
@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * Responsável por retornar todos os usuários do banco de dados
	 * @return
	 */
	public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
	
	/**
	 * Retorna todos os usuários ativos
	 * @return
	 */
	public List<Usuario> listarTodosAtivos(){
		return usuarioRepository.findByAtivo(1);
	}
	
	/**
	 * Buscar o usuário de acordo com o id digitado
	 * @param id
	 * @return
	 */
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
     
    /**
     * Reponsável por cadastrar um usuário
     * @param usuario
     * @return
     */
    public Usuario adicionar(@Valid Usuario usuario) {
        return usuarioRepository.saveAndFlush(usuario);
    }
    
    public Usuario atualizar(@Valid Usuario usuario) {
        return usuarioRepository.saveAndFlush(usuario);
    }
    
     
    /**
     * Responsável por remover um usuário
     * @param id
     */
    public void remover(Long id) {
    	Usuario usuario = buscarPorId(id);
    	usuario.setAtivo(0);
    	usuarioRepository.saveAndFlush(usuario);
    }
    
    
    public List<Usuario> filtrar(UsuarioFilter usuarioFilter){
    	return usuarioRepository.filtrar(usuarioFilter);
    }
    
    public Usuario findUsuariobyEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	public Optional<Usuario> findUsuarioByTokenResetarSenha(String token) {
		return usuarioRepository.findUsuarioByTokenResetarSenha(token);
	}

	public void updatePassword(String novaSenha, Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if(usuario.isPresent()) {
			usuario.get().setSenha(novaSenha);
			usuarioRepository.saveAndFlush(usuario.get());
		}
		
	}
  
	
}
