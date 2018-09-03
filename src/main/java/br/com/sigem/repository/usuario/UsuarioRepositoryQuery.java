package br.com.sigem.repository.usuario;

import java.util.List;


import br.com.sigem.model.Usuario;
import br.com.sigem.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {

	public List<Usuario> filtrar(UsuarioFilter usuarioFilter);	
	
	
}
