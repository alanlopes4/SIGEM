package br.com.sigem.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.DecimalFormat;

import br.com.sigem.model.relatorio.EntradaProduto;
import br.com.sigem.service.EntradaProdutoService;

@Controller
@RequestMapping("/sigem/relatorios/entradaProduto")
public class EntradaProdutoController {
	
	@Autowired
	private EntradaProdutoService entradaProdutoService;
	
	
	@GetMapping 
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("relatorio/EntradaProduto/index");
		
		List<EntradaProduto> entradas = entradaProdutoService.listarTodos();
		double valorTotalCompra = entradas.stream().mapToDouble(EntradaProduto::getPrecoLoteComprado).sum();
	    
		mv.addObject("entradas", entradas);
		mv.addObject("valorTotalCompra", String.format("%.2f",valorTotalCompra));
		
		return mv;
	}
	
	
	@PostMapping("/filtrar")
	public ModelAndView filtrar(@RequestParam("dataInicio") String dataInicio, @RequestParam("dataFim") String dataFim) {
		ModelAndView mv = new ModelAndView("relatorio/EntradaProduto/index");
		LocalDate localDateInicio, localDateFim;
		if(dataInicio != null && !dataInicio.equals("") && dataFim != null && !dataFim.equals("")) {
			localDateInicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			localDateFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			List<EntradaProduto> entradas = entradaProdutoService.filtrar(localDateInicio, localDateFim);
			double valorTotalCompra = entradas.stream().mapToDouble(EntradaProduto::getPrecoLoteComprado).sum();
			int qtdTotalCompra = entradas.stream().mapToInt(EntradaProduto::getQtdComprada).sum();
			mv.addObject("entradas", entradas);
			mv.addObject("valorTotalCompra", valorTotalCompra);
			mv.addObject("qtdTotalCompra", qtdTotalCompra);
			
		}else {
			List<EntradaProduto> entradas = entradaProdutoService.listarTodos();
			double valorTotalCompra = entradas.stream().mapToDouble(EntradaProduto::getPrecoLoteComprado).sum();
			int qtdTotalCompra = entradas.stream().mapToInt(EntradaProduto::getQtdComprada).sum();
			mv.addObject("entradas", entradas);
			mv.addObject("valorTotalCompra", valorTotalCompra);
			mv.addObject("qtdTotalCompra", qtdTotalCompra);
		}
		return mv;
	}
	
	
}
