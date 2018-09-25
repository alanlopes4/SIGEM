package br.com.sigem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.com.sigem.model.Email;

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
	            String html = templateEngine.process("email/email-template", context);

	            helper.setTo(email.getDestinatario());
	            helper.setText(html, true);
	            helper.setSubject(email.getAssunto());
	            helper.setFrom(email.getRemetente());

	            emailSender.send(message);
	        } catch (Exception e){
	            throw new RuntimeException(e);
	        }
	    }
	
}
