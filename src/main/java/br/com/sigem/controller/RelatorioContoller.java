package br.com.sigem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Classe responsável pelos requests vindos da web
 * @author sylar
 *
 */

@Controller
@RequestMapping("sigem/relatorios")
public class RelatorioContoller {
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("relatorio/index");
		return mv;
	}
	

}
