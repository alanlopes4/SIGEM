package br.com.sigem.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sigem.model.Estoque;
import br.com.sigem.model.FileModel;
import br.com.sigem.model.Produto;
import br.com.sigem.repository.filter.EstoqueFilter;
import br.com.sigem.repository.filter.ProdutoFilter;
import br.com.sigem.service.EstoqueService;
import br.com.sigem.service.ProdutoService;

@Controller
@RequestMapping("/sigem/baixaProdutos")
public class BaixarProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private EstoqueService estoqueService;
	
	@GetMapping 
	public ModelAndView index() {
		FileModel file = new FileModel();
		ModelAndView mv = new ModelAndView("/baixaProduto/index");
		mv.addObject(file);
		return mv;
	}
	
	@PostMapping
	public ModelAndView baixaProdutos(@Validated FileModel file, BindingResult result, ModelMap model) throws IOException {
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/rafaelf/git/SIGEM/test.txt"));
		try {
		    String line = bufferedReader.readLine();

		    while (line != null) {
		        line = bufferedReader.readLine();
		        String[] content = line.split(";");
		        int id = Integer.parseInt(content[0]);
		        int quantidade = Integer.parseInt(content[1]);
		        
		        ProdutoFilter produtoFilter = new ProdutoFilter();
		        produtoFilter.setCodigo(id);
		        Produto produto = produtoService.filtrar(produtoFilter).get(0);
				EstoqueFilter estoqueFilter = new EstoqueFilter();
				estoqueFilter.setProduto(produto);
				Estoque estoque = estoqueService.filtrar(estoqueFilter).get(0);
				
				quantidade = estoque.getGondola() - quantidade;
				quantidade = quantidade <= 0 ? 0 : quantidade;
				estoque.setGondola(quantidade);
				produto.setQuantidade(estoque.getGondola() + estoque.getEstoque());
				
				estoqueService.atualizar(estoque);
				produtoService.atualizar(produto);
		    }
		} finally {
		    bufferedReader.close();
		}
		
		return new ModelAndView("redirect:/sigem/baixaProdutos").addObject("sucesso", true);
	}


}
