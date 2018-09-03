package br.com.sigem.repository.historicoPreco;

import java.time.LocalDate;
import java.util.List;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.HistoricoPreco;

public interface HistoricoPrecoRepositoryQuery {

	public List<HistoricoPreco> filtrar(LocalDate dataInicio, LocalDate dataFim);	

	public HistoricoPreco filtrarPorProduto(Produto produto);
}
