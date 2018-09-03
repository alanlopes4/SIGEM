package br.com.sigem.model.relatorio;

import java.util.Optional;

import br.com.sigem.model.Produto;

public class RelatorioFactory extends BaseRelatorioFactory{

	@Override
	public Relatorio criarRelatorio(String tipo, Produto p, Optional<HistoricoPreco> hp) {
		Relatorio relatorio;
        switch (tipo.toLowerCase())
        {
            case "historicopreconovo":
                relatorio = new HistoricoPreco();
                break;
            case "historicoprecoatualizado":
                relatorio = new HistoricoPreco();
                break;
            case "entradaproduto":
                relatorio = new EntradaProduto();
                break;
            default: throw new IllegalArgumentException("Nenhum relatorio");
        }
 
        relatorio.criarRelatorio(p, hp);
        return relatorio;
	}

}
