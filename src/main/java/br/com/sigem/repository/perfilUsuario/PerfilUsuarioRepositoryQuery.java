package br.com.sigem.repository.perfilUsuario;

import java.util.List;

import br.com.sigem.model.PerfilUsuario;
import br.com.sigem.repository.filter.PerfilUsuarioFilter;

public interface PerfilUsuarioRepositoryQuery {

	public List<PerfilUsuario> filtrar(PerfilUsuarioFilter perfilUsuarioFilter);	
	
	
}
