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
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.sigem.model.Produto;
import br.com.sigem.util.AdapterLocalDate;

@Entity
public class HistoricoRetiradaProduto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Produto produto;
	
	@Column(nullable = false)
	@Convert(converter = AdapterLocalDate.class)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate dataRetirada;
	
	@Column(nullable = false)
	@NotBlank(message = "Motivo é uma informação obrigatória")
	private String motivo;

	@Column(nullable = false)
	private int quantidade;
	
	public HistoricoRetiradaProduto() {}
	
	public HistoricoRetiradaProduto(Produto produto, LocalDate dataRetirada, String motivo, int quantidade) {
		this.produto = produto;
		this.dataRetirada = dataRetirada;
		this.motivo = motivo;
		this.quantidade = quantidade;
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

	public LocalDate getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(LocalDate dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	
	
	
	

}
