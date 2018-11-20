package br.com.sigem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sigem.model.Produto;
import br.com.sigem.repository.filter.ProdutoFilter;
import br.com.sigem.service.ProdutoService;

@Controller
@RequestMapping("/sigem/relatorios/produtoValidade")
public class ProdutoValidadeController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping 
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("relatorio/produtoValidade/index");
		ProdutoFilter produtoFilter = new ProdutoFilter();
		produtoFilter.setAtivo(1);
		List<Produto> produtos = produtoService.filtrarValidade(produtoFilter);
		mv.addObject("validades", produtos);
		
		
		return mv;
	}
	/*@GetMapping 
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("relatorio/produtoValidade/index");
		
		List<ProdutoValidade> validades = ProdutoValidadeService.listarTodos();
	    
		mv.addObject("validades", validades);
		
		return mv;
	}
	
	
	@PostMapping("/filtrar")
	public ModelAndView filtrar(@RequestParam("dataInicio") String dataInicio, @RequestParam("dataFim") String dataFim) {
		ModelAndView mv = new ModelAndView("relatorio/produtoValidade/index");
		LocalDate localDateInicio, localDateFim;
		if(dataInicio != null && !dataInicio.equals("") && dataFim != null && !dataFim.equals("")) {
			localDateInicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			localDateFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			List<ProdutoValidade> validades = ProdutoValidadeService.filtrar(localDateInicio, localDateFim);
			mv.addObject("validades", validades);
			
		}else {
			List<ProdutoValidade> validades = ProdutoValidadeService.listarTodos();
			mv.addObject("validades", validades);
		}
		return mv;
	}*/
	

}
