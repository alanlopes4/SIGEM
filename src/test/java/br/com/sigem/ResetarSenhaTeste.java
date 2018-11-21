package br.com.sigem;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sigem.model.Email;
import br.com.sigem.model.TokenResetarSenha;
import br.com.sigem.model.Usuario;
import br.com.sigem.service.EmailService;
import br.com.sigem.service.UsuarioService;

public class ResetarSenhaTeste extends SigemApplicationTests {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	public void email_existente() {
		
		Email email = new Email();
		email.setAssunto("Email Teste");
		email.setDestinatario("ra101971@uem.br");
		Usuario usuario = usuarioService.findUsuariobyEmail(email.getDestinatario());
		
		Object[] resultado = emailService.sendEmail(email, usuario);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Email enviado");
	}
	
	@Test
	public void email_invalido() {
		
		Email email = new Email();
		email.setAssunto("Email Teste");
		email.setDestinatario("teste@teste.br");
		Usuario usuario = usuarioService.findUsuariobyEmail(email.getDestinatario());
		
		Object[] resultado = emailService.sendEmail(email, usuario);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Usuário não existente");
	}
	
	@Test 
	public void token_valido() {
		
		Usuario usuario = usuarioService.findUsuariobyEmail("ra101971@uem.br");
		TokenResetarSenha token = new TokenResetarSenha();
        token.setToken(UUID.randomUUID().toString());
        token.setUsuario(usuario);
        token.setDataExpiracao(30);
        usuario.setTokenResetarSenha(token.getToken());
        
        Object[] resultado = usuarioService.updatePassword(token, usuario);
        
        Assertions.assertThat(resultado[0]).isEqualTo("Senha alterada");
	}
	
	@Test 
	public void token_expirado() {
		
		Usuario usuario = usuarioService.findUsuariobyEmail("ra101971@uem.br");
		TokenResetarSenha token = new TokenResetarSenha();
		token.setToken(UUID.randomUUID().toString());
		token.setUsuario(usuario);
		token.setDataExpiracao(0);
		usuario.setTokenResetarSenha(token.getToken());
		
		Object[] resultado = usuarioService.updatePassword(token, usuario);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Token expirado");
	}
	
	@Test 
	public void token_invalido() {
		
		Usuario usuario = usuarioService.findUsuariobyEmail("ra101971@uem.br");
		TokenResetarSenha token = new TokenResetarSenha();
        token.setUsuario(usuario);
        token.setDataExpiracao(30);
        
        Object[] resultado = usuarioService.updatePassword(token, usuario);
        
        Assertions.assertThat(resultado[0]).isEqualTo("Token não existe");
	}
	
}
