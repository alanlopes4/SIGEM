package br.com.sigem.repository.filter;

public class EstoqueFilter {
	
	private Long id;
	private String nome;
	private Integer gondula;
	private Integer estoque;
	private Integer estoqueMinimo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer quantidade) {
		this.estoque = quantidade;
	}
	public Integer getGondula() {
		return gondula;
	}
	public void setGondula(int gondula) {
		this.gondula = gondula;
	}
	public Integer getEstoqueMinimo() {
		return estoqueMinimo;
	}
	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}
	
}
