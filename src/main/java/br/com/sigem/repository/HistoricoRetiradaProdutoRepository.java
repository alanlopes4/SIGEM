package br.com.sigem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sigem.model.relatorio.HistoricoRetiradaProduto;
import br.com.sigem.repository.historicoRetiradaProduto.HistoricoRetiradaProdutoRepositoryQuery;

@Repository
public interface HistoricoRetiradaProdutoRepository extends JpaRepository<HistoricoRetiradaProduto, Long>, HistoricoRetiradaProdutoRepositoryQuery{	
	
	
}
