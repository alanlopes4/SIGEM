package br.com.sigem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import br.com.sigem.web.EsqueceuSenhaDTO;
import br.com.sigem.web.ResetarSenhaDTO;

@Controller
@RequestMapping("/reset-password")
public class ResetarSenhaController {
	
	@Autowired 
	private UsuarioService usuarioService;
    
	@Autowired 
	private TokenResetarSenhaRepository tokenRepository;
    

    @ModelAttribute("passwordResetForm")
    public EsqueceuSenhaDTO passwordReset() {
        return new EsqueceuSenhaDTO();
    }

    @GetMapping
    public ModelAndView displayResetPasswordPage(@RequestParam(required = false) String token,
                                           Model model) {

    	
    	ModelAndView mv = new ModelAndView("login/recuperarSenha/resetarSenha");
    	
        TokenResetarSenha resetToken = tokenRepository.findByToken(token);
        if (resetToken == null){
            model.addAttribute("error", "Could not find password reset token.");
        } else if (resetToken.isExpired()){
            model.addAttribute("error", "Token has expired, please request a new password reset.");
        } else {
            model.addAttribute("token", resetToken.getToken());
        }

        return mv;
    }

    @PostMapping
    @Transactional
    public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid ResetarSenhaDTO form,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
            redirectAttributes.addFlashAttribute("passwordResetForm", form);
            return "redirect:/reset-password?token=" + form.getToken();
        }

        TokenResetarSenha token = tokenRepository.findByToken(form.getToken());
        Usuario usuario = token.getUsuario();
        String updatedPassword = form.getSenha();
        usuarioService.updatePassword(updatedPassword, usuario.getId());
        tokenRepository.delete(token);

        return "redirect:/login?resetSuccess";
    }

}
