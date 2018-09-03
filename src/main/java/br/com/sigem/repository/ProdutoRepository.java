package br.com.sigem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sigem.model.ChaveProduto;
import br.com.sigem.model.Produto;
import br.com.sigem.repository.produto.ProdutoRepositoryQuery;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery{

	
}
