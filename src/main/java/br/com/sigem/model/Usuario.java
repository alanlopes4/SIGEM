package br.com.sigem.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.sigem.util.AdapterLocalDate;
/**
 * 
 * @author Alan Lopes
 * Classe entidade 
 * 
 * 
 */
@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank(message = "Nome é uma informação obrigatória")
	private String nome;
	@Column(nullable = false)
	@NotBlank(message = "Nome do usuário é uma informação obrigatória")
	private String nomeUsuario;
	@Embedded
	private Endereco endereco;
	@Column(nullable = false)
	@Email(message = "Entre com um email válido")
	@NotBlank(message = "Email é uma informação obrigatória")
	private String email;
	@Column(nullable = false)
	@NotBlank(message = "Senha é uma informação obrigatória")
	private String senha;
	@Column(nullable = false)
	@NotBlank(message = "CPF é uma informação obrigatória")
	@CPF
	private String cpf;
	@Column(nullable = false)
	@NotBlank(message = "Cargo é uma informação obrigatória")
	private String cargo;
	@Column(nullable = false)
	
	@Convert(converter = AdapterLocalDate.class)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@NotNull(message = "Data Nascimento é uma informação obrigatória")
	@Past(message="Data de nascimento inválida. Somente datas anteriores ao dia atual são permitidas.")
	private LocalDate dataNascimento;
	
	@Column(nullable = false, columnDefinition = "int(1) default 1")
	private int ativo;
	@OneToOne
	@NotNull(message = "Perfil de usuário é uma informação obrigatória")
	private PerfilUsuario perfilUsuario;
	@OneToMany
	private List<Produto> produtos;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_permissao",
            joinColumns =  @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns =  @JoinColumn(name = "permissao_id", referencedColumnName = "id"))
    private Set<Permissao> permissoes;
	
	
	@Transient
	private String confirmarEmail;
	@Transient
	private String confirmarSenha;
	
	public Usuario(){
		
	}
	
	public Usuario(String nome, String nomeUsuario, String email, String senha, Set<Permissao> permissoes) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.nomeUsuario = nomeUsuario;
		this.permissoes = permissoes;
	}
	
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getConfirmarEmail() {
		return confirmarEmail;
	}

	public void setConfirmarEmail(String confirmarEmail) {
		this.confirmarEmail = confirmarEmail;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public PerfilUsuario getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public int isAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Set<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
