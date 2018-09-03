package br.com.sigem.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Estoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Gondula é uma informação obrigatória.")
	private int gondula;
	@NotBlank(message = "Estoque é uma informação obrigatória.")
	private int estoque;
	@NotBlank(message = "EstoqueMinimo é uma informação obrigatória.")
	private int estoqueMinimo;
	
//	@OneToMany
//	@JoinColumn(name = "estoque_id")
//	private List<Produto> produtos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getGondula() {
		return gondula;
	}
	public void setGondula(int gondula) {
		this.gondula = gondula;
	}
	public int getEstoque() {
		return estoque;
	}
	public void setEstoque(int estoque) {
		this.estoque = estoque;
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
