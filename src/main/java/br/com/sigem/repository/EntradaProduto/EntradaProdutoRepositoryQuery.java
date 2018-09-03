package br.com.sigem.repository.EntradaProduto;

import java.time.LocalDate;
import java.util.List;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.EntradaProduto;

public interface EntradaProdutoRepositoryQuery {

	public List<EntradaProduto> filtrar(LocalDate dataInicio, LocalDate dataFim);
	public EntradaProduto filtrarPorProduto(Produto produto);
}
