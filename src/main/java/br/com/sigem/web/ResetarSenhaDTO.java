package br.com.sigem.web;

import org.hibernate.validator.constraints.NotEmpty;

public class ResetarSenhaDTO {
	
	@NotEmpty
    private String senha;

    @NotEmpty
    private String confirmarSenha;

    @NotEmpty
    private String token;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
