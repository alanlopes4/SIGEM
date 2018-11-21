package br.com.sigem.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.sigem.util.AdapterLocalDate;
/**
 * 
 * @author Viviane Shiraishi e Rafael Freire
 * Classe entidade 
 * 
 */
@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Código é uma informação obrigatória")
	private String codigo;
	
	@Column(nullable = false)
	@NotBlank(message = "Nome é uma informação obrigatória")
	private String nome;
	
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Lote do produto é uma informação obrigatória")
	private String lote;
	
	@Column(nullable = false)
	@NotBlank(message = "Marca é uma informação obrigatória")
	private String marca;
	
	@Column(nullable = false)
	@NotNull(message = "Quantidade é uma informação obrigatória")
	private Integer quantidade;
	
	@Column(nullable = false)
	@NotNull(message = "Quantidade mínima é uma informação obrigatória")
	private Integer quantidadeMinima;
	
	@Column(nullable = false)
	@NotNull(message = "Preço unitário do produto é uma informação obrigatória")
	private Double precoUnitario;
	
	@Column(nullable = false)
	@NotNull(message = "Preço do lote do produto é uma informação obrigatória")
	private Double precoLote;
	
	@Convert(converter = AdapterLocalDate.class)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@NotNull(message = "Data da compra do lote do produto é uma informação obrigatória")
	private LocalDate dataCompra;
	
	@Column(nullable = true)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Convert(converter = AdapterLocalDate.class)
	private LocalDate validade;
	
	@Column(nullable = true)
	private Boolean pericivel;
	
	@Column(nullable = true)
	private String descricao;
	
	@Column(nullable = false, columnDefinition = "int(1) default 1")
	private int ativo;
	
	public Produto() {}
	
	public Produto(Long id,String nome,
			 String lote,
			 String marca,
			 Integer quantidade,
			 Integer quantidadeMinima,
			 Double precoUnitario,
			 Double precoLote,
			 LocalDate dataCompra,
			LocalDate validade, Boolean pericivel,
			String descricao) {
		
		this.id = id;
		this.nome = nome;
		this.lote = lote;
		this.marca = marca;
		this.quantidade = quantidade;
		this.quantidadeMinima = quantidadeMinima;
		this.precoUnitario = precoUnitario;
		this.precoLote = precoLote;
		this.dataCompra = dataCompra;
		this.validade = validade;
		this.pericivel = pericivel;
		this.descricao = descricao;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Integer getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Double getPrecoLote() {
		return precoLote;
	}

	public void setPrecoLote(Double precoLote) {
		this.precoLote = precoLote;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public LocalDate getValidade() {
		return validade;
	}

	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}

	public Boolean getPericivel() {
		return pericivel;
	}

	public void setPericivel(Boolean pericivel) {
		this.pericivel = pericivel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public int isAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
