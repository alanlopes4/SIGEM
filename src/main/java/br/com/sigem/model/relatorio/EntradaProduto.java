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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.sigem.model.Produto;
import br.com.sigem.util.AdapterLocalDate;

@Entity
public class EntradaProduto extends Relatorio{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Produto produto;
	
	@Column(nullable = false)
	@Convert(converter = AdapterLocalDate.class)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate dataCompra;
	
	@Transient
	private Double valorTotalComprado;
	
	@Column(nullable = false)
	private Double precoLoteComprado;
	
	@Transient
	private Long qtdTotalComprada;
	
	@Column(nullable = false)
	private int qtdComprada;
	
	
	public EntradaProduto() {}
	
	public EntradaProduto(Produto produto, LocalDate dataCompra, Double precoLoteComprado, int qtdComprada) {
		this.produto = produto;
		this.dataCompra = dataCompra;
		this.precoLoteComprado = precoLoteComprado;
		this.qtdComprada = qtdComprada;
	}
	
	
	@Override
	public void criarRelatorio(Produto p, Optional<HistoricoPreco> hp) {
		this.produto = p;
		this.dataCompra = p.getDataCompra();
		this.precoLoteComprado = p.getPrecoLote();
		this.qtdComprada = p.getQuantidade();
		
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

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Double getValorTotalComprado() {
		return valorTotalComprado;
	}

	public void setValorTotalComprado(Double valorTotalComprado) {
		this.valorTotalComprado = valorTotalComprado;
	}

	public Double getPrecoLoteComprado() {
		return precoLoteComprado;
	}

	public void setPrecoLoteComprado(Double precoLoteComprado) {
		this.precoLoteComprado = precoLoteComprado;
	}

	public Long getQtdTotalComprada() {
		return qtdTotalComprada;
	}

	public void setQtdTotalComprada(Long qtdTotalComprada) {
		this.qtdTotalComprada = qtdTotalComprada;
	}

	public int getQtdComprada() {
		return qtdComprada;
	}

	public void setQtdComprada(int qtdComprada) {
		this.qtdComprada = qtdComprada;
	}

	
	
	
	
}
