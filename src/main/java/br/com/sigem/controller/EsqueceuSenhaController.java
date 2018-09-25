package br.com.sigem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sigem.model.Email;
import br.com.sigem.model.TokenResetarSenha;
import br.com.sigem.model.Usuario;
import br.com.sigem.repository.TokenResetarSenhaRepository;
import br.com.sigem.service.EmailService;
import br.com.sigem.service.UsuarioService;
import br.com.sigem.web.EsqueceuSenhaDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class EsqueceuSenhaController {
	
	@Autowired 
	private UsuarioService usuarioService;
    
	@Autowired 
    private TokenResetarSenhaRepository tokenRepository;
    
	@Autowired 
    private EmailService emailService;

    @ModelAttribute("forgotPasswordForm")
    public EsqueceuSenhaDTO forgotPasswordDto() {
        return new EsqueceuSenhaDTO();
    }

    @GetMapping
    public ModelAndView displayForgotPasswordPage() {
    	
    	ModelAndView mv = new ModelAndView("login/recuperarSenha/esqueceuSenha");
    	
        return mv;
    }

    @PostMapping
    public ModelAndView processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid EsqueceuSenhaDTO form,
                                            BindingResult result,
                                            HttpServletRequest request) {

    	ModelAndView mv = new ModelAndView("login/recuperarSenha/esqueceuSenha");
    	
        if (result.hasErrors()){
            return mv;
        }

        Usuario usuario = usuarioService.findUsuariobyEmail(form.getEmail());
        if (usuario == null){
            result.rejectValue("email", null, "We could not find an account for that e-mail address.");
            return mv;
        }

        TokenResetarSenha token = new TokenResetarSenha();
        token.setToken(UUID.randomUUID().toString());
        token.setUsuario(usuario);
        token.setDataExpiracao(30);
        tokenRepository.save(token);

        Email mail = new Email();
        mail.setRemetente("no-reply@memorynotfound.com");
        mail.setDestinatario(usuario.getEmail());
        mail.setAssunto("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", usuario);
        model.put("signature", "Teste");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModelo(model);
        emailService.sendEmail(mail);

        return mv;

    }
}
