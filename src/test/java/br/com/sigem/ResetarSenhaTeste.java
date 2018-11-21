package br.com.sigem;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sigem.model.Email;
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
	
}
