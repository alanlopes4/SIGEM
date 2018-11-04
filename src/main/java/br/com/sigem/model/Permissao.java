package br.com.sigem.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.Set;







@Entity
@Table(name = "permissao")
public class Permissao{
	
	
	public static final int ROLE_USUARIOS = 1;
	public static final int ROLE_PRODUTOS = 2;
	public static final int ROLE_RELATORIOS = 3;
	public static final int ROLE_RELATORIOS_HISTORICOPRECO = 4;
	public static final int ROLE_RELATORIOS_ENTRADAPRODUTO = 5;
	public static final int ROLE_RELATORIOS_ESTOQUEDISPONIVEL = 6;
	public static final int ROLE_USUARIOS_CONSULTAR = 7;
	public static final int ROLE_USUARIOS_CADASTRAR = 8;
	public static final int ROLE_USUARIOS_EDITAR = 9;
	public static final int ROLE_USUARIOS_EXCLUIR = 10;
	public static final int ROLE_PRODUTOS_CONSULTAR = 11;
	public static final int ROLE_PRODUTOS_CADASTRAR = 12;
	public static final int ROLE_PRODUTOS_EDITAR = 13;
	public static final int ROLE_PRODUTOS_EXCLUIR = 14;
	public static final int ROLE_PRODUTOS_BAIXA = 15;
	public static final int ROLE_PERFIL = 16;
	public static final int ROLE_PERFIL_CONSULTAR = 17;
	public static final int ROLE_PERFIL_CADASTRAR = 18;
	public static final int ROLE_PERFIL_EDITAR = 19;
	public static final int ROLE_PERFIL_EXCLUIR = 20;
	public static final int ROLE_ESTOQUE = 21;
	
	
	
	

	public Permissao( String nome) {
		this.nome = nome;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotEmpty
    @Column(unique = true)
    private String nome;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissoes")
    private Set<Usuario> usuarios;


    public Permissao() {};
    
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

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
