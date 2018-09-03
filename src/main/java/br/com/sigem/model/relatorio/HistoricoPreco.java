package br.com.sigem.model.relatorio;


import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.sigem.model.Produto;
import br.com.sigem.util.AdapterLocalDate;

@Entity
public class HistoricoPreco extends Relatorio{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Produto produto;
	
	@Column(nullable = false)
	@Convert(converter = AdapterLocalDate.class)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate dataAlteracao;
	
	
	@Column(nullable = false)
	private Double precoAnterior;
	
	@Column(nullable = false)
	private Double precoAtual;

	
	
	public HistoricoPreco() {}
	
	public HistoricoPreco(Produto produto, LocalDate dataAlteracao, Double precoAnterior, Double precoAtual) {
		this.produto = produto;
		this.dataAlteracao = dataAlteracao;
		this.precoAnterior = precoAnterior;
		this.precoAtual = precoAtual;
	}
	
	@Override
	public void criarRelatorio(Produto p, Optional<HistoricoPreco> hp) {
		this.produto = p;
		if(hp != null) {
			this.precoAnterior = hp.get().getPrecoAtual();
			this.dataAlteracao = LocalDate.now();
		}
		else {
			this.dataAlteracao = p.getDataCompra();
			this.precoAnterior = p.getPrecoUnitario();
		}
		this.precoAtual = p.getPrecoUnitario();		
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public LocalDate getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(LocalDate dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Double getPrecoAnterior() {
		return precoAnterior;
	}

	public void setPrecoAnterior(Double precoAnterior) {
		this.precoAnterior = precoAnterior;
	}

	public Double getPrecoAtual() {
		return precoAtual;
	}

	public void setPrecoAtual(Double precoAtual) {
		this.precoAtual = precoAtual;
	}

	
	
	
	
	
	

}
