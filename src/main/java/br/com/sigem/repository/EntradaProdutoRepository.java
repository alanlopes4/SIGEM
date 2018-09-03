package br.com.sigem.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.EntradaProduto;
import br.com.sigem.repository.EntradaProduto.EntradaProdutoRepositoryQuery;

@Repository
public interface EntradaProdutoRepository extends JpaRepository<EntradaProduto, Long>, EntradaProdutoRepositoryQuery{

	public EntradaProduto findByProduto(Produto produto);
	
}
