package br.com.sigem.model;

import java.util.Map;

public class Email {

	private String remetente;
    private String destinatario;
    private String assunto;
    private Map<String, Object> modelo;

    public Email() {

    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Map<String, Object> getModelo() {
        return modelo;
    }

    public void setModelo(Map<String, Object> modelo) {
        this.modelo = modelo;
    }
	
}
