package br.com.sigem.repository.filter;


import br.com.sigem.model.Produto;

public class EstoqueFilter {
	
	private Long id;
	private Integer gondola;
	private Integer estoque;
	private Integer estoqueMinimo;
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
	public Integer getGondola() {
		return gondola;
	}
	public void setGondola(Integer gondola) {
		this.gondola = gondola;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Integer getAdicionadaGondola() {
		return adicionadaGondola;
	}
	public void setAdicionadaGondola(Integer adicionadaGondola) {
		this.adicionadaGondola = adicionadaGondola;
	}
	
	public Integer getRetiradoGondola() {
		return retiradoGondola;
	}
	public void setRetiradoGondola(Integer retiradoGondola) {
		this.retiradoGondola = retiradoGondola;
	}
	public Integer getEstoqueMinimo() {
		return estoqueMinimo;
	}
	public void setEstoqueMinimo(Integer estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}
	
	public void descontarQuantidade(Integer quantidade) {
		this.estoque -= quantidade;
	}
	
	public void alterarQuantidade(Integer quantidade) {
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
