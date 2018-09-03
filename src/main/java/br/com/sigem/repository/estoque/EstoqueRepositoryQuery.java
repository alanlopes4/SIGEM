package br.com.sigem.repository.estoque;

import java.util.List;

import br.com.sigem.model.Estoque;
import br.com.sigem.repository.filter.EstoqueFilter;

public interface EstoqueRepositoryQuery {

	public List<Estoque> filtrar(EstoqueFilter estoqueFilter);	

	
}
