package br.com.sigem.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class PerfilUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome é uma informação obrigatória.")
	private String nome;
	@NotNull(message = "Permissões é uma informação obrigatória.")
	@Column(length=100000)
	private String[] permissoes; //ROLE_PRODUTOS, ROLE_PRODUTOS_CONSULTAR
	@Column(nullable = false, columnDefinition = "int(1) default 1")
	private int ativo;
	
	public PerfilUsuario(){}
	
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


	public String[] getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(String[] permissoes) {
		this.permissoes = permissoes;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
		
	
}
