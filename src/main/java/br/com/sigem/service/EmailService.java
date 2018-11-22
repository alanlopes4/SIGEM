package br.com.sigem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.com.sigem.model.Email;
import br.com.sigem.model.Usuario;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
	
	 	@Autowired
	    private JavaMailSender emailSender;

	    @Autowired
	    private SpringTemplateEngine templateEngine;

	    public void sendEmail(Email email) {
	        try {
	            MimeMessage message = emailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message,
	                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                    StandardCharsets.UTF_8.name());

	            Context context = new Context();
	            context.setVariables(email.getModelo());
	            String html = templateEngine.process("emailTemplate.html", context);

	            helper.setTo(email.getDestinatario());
	            helper.setText(html, true);
	            helper.setSubject(email.getAssunto());
	            helper.setFrom(email.getRemetente());

	            emailSender.send(message);
	        } catch (Exception e){
	            throw new RuntimeException(e);
	        }
	    }
	    
	    public Object[] sendEmail(Email email, Usuario usuario) {
			
			String mensagem = "";
			
			if(usuario == null) {
				mensagem = "Email não encontrado";
			}
			else {
				mensagem = "Verifique seu email";
			}

			Object[] obj = new Object[1];
			obj[0] = mensagem;
			
			return obj;
	    }
	
}
