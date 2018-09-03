package br.com.sigem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class UdsSecurityConfig {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder, 
			PasswordEncoder passwordEncoder, 
			GpUserDetailsService userDetailsService) throws Exception {
		builder
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder)
		.and()
			.inMemoryAuthentication()
			.withUser("user_gerencial").password("$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2").roles("PRODUTOS", "RELATORIOS")
			.and()
			.withUser("user_administrativo").password("$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2").roles("PRODUTOS","USUARIOS", "PERFIL", "RELATORIOS",
					"USUARIOS_CONSULTAR", "USUARIOS_CADASTRAR", "USUARIOS_EXCLUIR", "USUARIOS_EDITAR",
					"PRODUTOS_CONSULTAR", "PRODUTOS_CADASTRAR", "PRODUTOS_EXCLUIR", "PRODUTOS_EDITAR",
					"PERFIL_CONSULTAR", "PERFIL_CADASTRAR", "PERFIL_EXCLUIR", "PERFIL_EDITAR",
					"RELATORIOS_HISTORICOPRECO", "RELATORIOS_ENTRADAPRODUTO", "RELATORIOS_ESTOQUEDISPONIVEL")
        	.and()
        	.withUser("user_produto").password("$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2").roles("PRODUTOS")
        	.and()
        	.withUser("user_relatorio").password("$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2").roles("RELATORIOS");
		
	}
}
