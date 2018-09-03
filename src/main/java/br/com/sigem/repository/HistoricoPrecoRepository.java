package br.com.sigem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.HistoricoPreco;
import br.com.sigem.repository.historicoPreco.HistoricoPrecoRepositoryQuery;

@Repository
public interface HistoricoPrecoRepository extends JpaRepository<HistoricoPreco, Long>, HistoricoPrecoRepositoryQuery {
	
	
	/**
	 * Retorna um histórico de preço de acordo com o produto passado como parâmetro
	 * @param produto
	 * @return
	 */
	public HistoricoPreco findByProduto(Produto produto);
	
	public HistoricoPreco findFirstByProdutoOrderByDataAlteracaoDesc(Produto produto);
	
}
