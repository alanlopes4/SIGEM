package br.com.sigem.model.relatorio;

import java.util.Optional;

import br.com.sigem.model.Produto;

public abstract class Relatorio {
	public abstract void criarRelatorio(Produto p, Optional<HistoricoPreco> hp);
}
