package br.com.sigem.repository.historicoRetiradaProduto;

import java.time.LocalDate;
import java.util.List;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.HistoricoRetiradaProduto;

public interface HistoricoRetiradaProdutoRepositoryQuery {

	public List<HistoricoRetiradaProduto> filtrar(LocalDate dataInicio, LocalDate dataFim);	

	public HistoricoRetiradaProduto filtrarPorProduto(Produto produto);
}
