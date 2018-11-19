package br.com.sigem.repository.filter;


import br.com.sigem.model.Produto;

public class EstoqueFilter {
	
	private Long id;
	private int gondola;
	private int estoque;
	private int estoqueMinimo;
	private String mensagem;
	
	private int adicionadaGondola;
	
	private int retiradoGondola;
	
	
	private String motivo;
	
	private Produto produto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getGondola() {
		return gondola;
	}
	public void setGondola(int gondola) {
		this.gondola = gondola;
	}
	public int getEstoque() {
		return estoque;
	}
	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getAdicionadaGondola() {
		return adicionadaGondola;
	}
	public void setAdicionadaGondola(int adicionadaGondola) {
		this.adicionadaGondola = adicionadaGondola;
	}
	
	public int getRetiradoGondola() {
		return retiradoGondola;
	}
	public void setRetiradoGondola(int retiradoGondola) {
		this.retiradoGondola = retiradoGondola;
	}
	public int getEstoqueMinimo() {
		return estoqueMinimo;
	}
	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}
	
	public void descontarQuantidade(int quantidade) {
		this.estoque -= quantidade;
	}
	
	public void alterarQuantidade(int quantidade) {
		this.estoque += quantidade;
	}
	
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	
}
