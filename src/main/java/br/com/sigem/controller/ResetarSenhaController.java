package br.com.sigem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sigem.model.TokenResetarSenha;
import br.com.sigem.model.Usuario;
import br.com.sigem.repository.TokenResetarSenhaRepository;
import br.com.sigem.service.UsuarioService;
import br.com.sigem.web.ResetarSenhaDTO;

@Controller
@RequestMapping("sigem/resetarSenha")
public class ResetarSenhaController {
	
	@Autowired 
	private UsuarioService usuarioService;
    
	@Autowired 
	private TokenResetarSenhaRepository tokenRepository;
	
    @ModelAttribute("passwordResetForm")
    public ResetarSenhaDTO passwordReset() {
        return new ResetarSenhaDTO();
    }

    @GetMapping
    public ModelAndView mostrarPaginaResetarSenha(@RequestParam(required = false) String token,
                                           Model model) {

	
    	ModelAndView mv = new ModelAndView("login/resetarSenha");
    	
        TokenResetarSenha resetToken = tokenRepository.findByToken(token);
        if (resetToken == null){
            model.addAttribute("error", "Não foi possível encontrar o token para resetar sua senha.");
        } else if (resetToken.isExpired()){
            model.addAttribute("error", "Token expirou, por favor faça uma nova requisição.");
        } else {
            model.addAttribute("token", resetToken.getToken());
        }

        return mv;
    }

    @PostMapping
    @Transactional
    public ModelAndView resetarSenha(@ModelAttribute("passwordResetForm") @Valid ResetarSenhaDTO form,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {

    	 if(!(form.getSenha() == null && form.getConfirmarSenha() == null || 
         		form.getSenha() != null && form.getSenha().equals(form.getConfirmarSenha()))){
         	result.addError(new FieldError("usuario", "confirmarSenha", "As senhas digitadas não correspondem"));
         }
    	
    	if (result.hasErrors()){
            redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
            redirectAttributes.addFlashAttribute("passwordResetForm", form);
            return new ModelAndView("redirect:/sigem/resetarSenha?token=" + form.getToken());
        }
        
        TokenResetarSenha token = tokenRepository.findByToken(form.getToken());
        Usuario usuario = token.getUsuario();
        String updatedPassword = new BCryptPasswordEncoder().encode(form.getSenha());
        usuarioService.updatePassword(updatedPassword, usuario.getId());
        tokenRepository.delete(token);

        return new ModelAndView("redirect:/sigem/login").addObject("resetSuccess", true);
    }

}
