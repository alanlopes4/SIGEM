package br.com.sigem.model.relatorio;

import java.util.Optional;

import br.com.sigem.model.Produto;

public abstract class BaseRelatorioFactory {

	public abstract Relatorio criarRelatorio(String tipo, Produto produto, Optional<HistoricoPreco> hp);
	
}
