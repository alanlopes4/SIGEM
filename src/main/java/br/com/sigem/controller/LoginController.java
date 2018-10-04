package br.com.sigem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sigem.model.Usuario;

@Controller
@RequestMapping("/sigem/login")
public class LoginController {

	@GetMapping
	public ModelAndView index(Usuario usuario) {
		ModelAndView mv = new ModelAndView("login/login");
		mv.addObject("usuario", usuario);
		return mv;
	}
	
	@PostMapping
	public ModelAndView validar(Usuario usuario) {
		return new ModelAndView("redirect:/sigem/usuarios");
	}
	
	@PostMapping("/logout")
	public ModelAndView logout() {
		return new ModelAndView("redirect:/sigem/login");
	}
	
}
