package br.com.sigem.model;

public enum EnumPerfilUsuario {

	ADMINISTRATIVO("Administrativo"), GERENCIAL("Gerencial"), RESTRITO("Restrito");
	
	private final String perfil;
	
	EnumPerfilUsuario(String perfil) {
		this.perfil = perfil;
	}

	public String getPerfil() {
		return perfil;
	}
	
	
	
	
}
