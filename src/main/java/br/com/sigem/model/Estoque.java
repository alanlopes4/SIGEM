package br.com.sigem.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Classe responsavél por gerenciar as regras de negócios do estoque
 * @author Alan
 *
 */

@Entity
public class Estoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer gondola;
	private Integer estoque;
	private Integer estoqueMinimo;
	private String mensagem;
	
	@Transient
	private Integer adicionadaGondola;
	
	@Transient
	private Integer retiradoGondola;
	
	
	@Transient
	private String motivo;
	
	@OneToOne
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estoque other = (Estoque) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
