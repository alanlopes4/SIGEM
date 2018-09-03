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
@RequestMapping("/sigem/relatorios/estoqueDisponivel")
public class EstoqueDisponivelController {

	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping 
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("relatorio/estoqueDisponivel/index");
		ProdutoFilter produtoFilter = new ProdutoFilter();
		produtoFilter.setAtivo(1);
		List<Produto> produtos = produtoService.filtrar(produtoFilter);
		Integer qtdTotalEstoque = produtos.stream().mapToInt(Produto::getQuantidade).sum();
		mv.addObject("produtos", produtos);
		mv.addObject("qtdTotalEstoque", qtdTotalEstoque);
		
		
		return mv;
	}
	
}
