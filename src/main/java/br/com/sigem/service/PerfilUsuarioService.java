package br.com.sigem.service;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigem.model.PerfilUsuario;
import br.com.sigem.repository.PerfilUsuarioRepository;
import br.com.sigem.repository.filter.PerfilUsuarioFilter;
/**
 * 
 * @author Alan Lopes
 *
 */
@Service
public class PerfilUsuarioService {
	
	@Autowired
	private PerfilUsuarioRepository perfilUsuarioRepository;

	/**
	 * Responsável por retornar todos os perfis do banco de dados
	 * @return
	 */
	public List<PerfilUsuario> listarTodos() {
        return perfilUsuarioRepository.findAll();
    }
	
	public List<PerfilUsuario> listarTodosAtivos(){
		return perfilUsuarioRepository.findByAtivo(1);
	}
     
	/**
	 * Responsável por buscar o perfil de acordo com o id digitado
	 * @param id
	 * @return
	 */
    public PerfilUsuario buscarPorId(Long id) {
        return perfilUsuarioRepository.findById(id).orElse(null);
    }
     
    /**
     * Reponsável por cadastrar um perfil
     * @param perfilUsuario
     * @return
     */
    public PerfilUsuario adicionar(@Valid PerfilUsuario perfilUsuario) {
    	perfilUsuario.setAtivo(1);
        return perfilUsuarioRepository.saveAndFlush(perfilUsuario);
    }
    
    public PerfilUsuario atualizar(@Valid PerfilUsuario PerfilUsuario) {
        return perfilUsuarioRepository.saveAndFlush(PerfilUsuario);
    }
    
     
    /**
     * Responsável por remover um perfil
     * @param id
     */
    public void remover(Long id) {
    	PerfilUsuario PerfilUsuario = buscarPorId(id);
    	perfilUsuarioRepository.delete(PerfilUsuario);
    }
    
    
    public List<PerfilUsuario> filtrar(PerfilUsuarioFilter PerfilUsuarioFilter){
    	return perfilUsuarioRepository.filtrar(PerfilUsuarioFilter);
    }
  
	
}
