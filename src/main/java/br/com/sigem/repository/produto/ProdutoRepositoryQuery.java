package br.com.sigem.repository.produto;

import java.util.List;

import br.com.sigem.model.Produto;
import br.com.sigem.repository.filter.ProdutoFilter;

public interface ProdutoRepositoryQuery {

	public List<Produto> filtrar(ProdutoFilter produtoFilter);	

	
}
