package br.com.sigem.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Endereco {

	@Column(nullable = false)
	@NotBlank(message = "Rua é uma informação obrigatória")
	private String rua;
	@Column(nullable = false)
	@NotBlank(message = "Número é uma informação obrigatória")
	private Integer numero;
	private String complemento;
	@Column(nullable = false)
	@NotBlank(message = "Bairro é uma informação obrigatória")
	private String bairro;
	@Column(nullable = false)
	@NotBlank(message = "CEP é uma informação obrigatória")
	private String CEP;
	@Column(nullable = false)
	@NotBlank(message = "Cidade é uma informação obrigatória")
	private String Cidade;
	
	
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCEP() {
		return CEP;
	}
	public void setCEP(String cEP) {
		CEP = cEP;
	}
	public String getCidade() {
		return Cidade;
	}
	public void setCidade(String cidade) {
		Cidade = cidade;
	}
	
	
	
	
}
