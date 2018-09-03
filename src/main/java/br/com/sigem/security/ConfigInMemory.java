package br.com.sigem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

public class ConfigInMemory {
		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
			builder
				.inMemoryAuthentication()
				.withUser("admin").password("123").roles("ROLE_USUARIOS", "ROLE_RELATORIOS", "ROLE_PRODUTOS");
		}
	
	
}
