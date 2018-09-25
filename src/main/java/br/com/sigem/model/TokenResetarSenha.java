package br.com.sigem.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class TokenResetarSenha {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private Date dataExpiracao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public void setDataExpiracao(int minutos){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutos);
        this.dataExpiracao = now.getTime();
    }

    public boolean isExpired() {
        return new Date().after(this.dataExpiracao);
    }
}
