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
@RequestMapping("sigem/esqueceuSenha")
public class EsqueceuSenhaController {
	
	@Autowired 
	private UsuarioService usuarioService;
    
	@Autowired 
    private TokenResetarSenhaRepository tokenRepository;
    
	@Autowired 
    private EmailService emailService;

    @ModelAttribute("forgotPasswordForm")
    public EsqueceuSenhaDTO esqueceuSenhaDto() {
        return new EsqueceuSenhaDTO();
    }

    @GetMapping
    public ModelAndView mostrarPaginaEsqueceuSenha() {
    	ModelAndView mv = new ModelAndView("login/esqueceuSenha");
        return mv;
        
    }

    @PostMapping
    public ModelAndView processarFormEsqueceuSenha(@ModelAttribute("forgotPasswordForm") @Valid EsqueceuSenhaDTO form,
                                            BindingResult result,
                                            HttpServletRequest request) {

        if (result.hasErrors()){
            return new ModelAndView("redirect:/sigem/esqueceuSenha").addObject("erro", true);
        }

        Usuario usuario = usuarioService.buscarPorEmail(form.getEmail());
        if (usuario == null){
            result.rejectValue("email", null, "Não foi possível encontrar o endereço de email.");
            return new ModelAndView("redirect:/sigem/esqueceuSenha").addObject("invalido", true);
        }

        TokenResetarSenha token = new TokenResetarSenha();
        token.setToken(UUID.randomUUID().toString());
        token.setUsuario(usuario);
        token.setDataExpiracao(30);
        tokenRepository.save(token);

        Email mail = new Email();
        mail.setRemetente("SIGEMISSUEM@gmail.com");
        mail.setDestinatario(usuario.getEmail());
        mail.setAssunto("Pedido de resetar senha");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", usuario);
        model.put("signature", "Teste");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", url + "/sigem/resetarSenha?token=" + token.getToken());
        mail.setModelo(model);
        emailService.sendEmail(mail);

        return new ModelAndView("redirect:/sigem/login").addObject("sucesso", true);

    }
}
