package br.com.sigem.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.sigem.service.HistoricoPrecoService;

@Controller
@RequestMapping("/sigem/relatorios/historicoPreco")
public class HistoricoPrecoController {
	
	
	@Autowired
	private HistoricoPrecoService historicoPrecoService;
	
	
	@GetMapping 
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("relatorio/historicoPreco/index");
		mv.addObject("historicos", historicoPrecoService.listarTodos());
		return mv;
	}
	
	
	@PostMapping("/filtrar")
	public ModelAndView filtrar(@RequestParam("dataInicio") String dataInicio, @RequestParam("dataFim") String dataFim) {
		ModelAndView mv = new ModelAndView("relatorio/historicoPreco/index");
		LocalDate localDateInicio, localDateFim;
		if(dataInicio != null && !dataInicio.equals("") && dataFim != null && !dataFim.equals("")) {
			localDateInicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			localDateFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			mv.addObject("historicos", historicoPrecoService.filtrar(localDateInicio, localDateFim));
			return mv;
		}
		else {
			mv.addObject("historicos", historicoPrecoService.listarTodos());
			return mv;
		}
		
	}

	
}
