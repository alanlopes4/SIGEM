package br.com.sigem.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.sigem.model.Produto;
import br.com.sigem.repository.filter.ProdutoFilter;
import br.com.sigem.service.ProdutoService;

@Controller
@RequestMapping("/sigem/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	
	@GetMapping
    public ModelAndView findAll(@RequestParam("produtoFilter")Optional<ProdutoFilter> produtoFilter) {
		
        ModelAndView modelView = new ModelAndView("produto/index");
        modelView.addObject("produtos", produtoService.listarTodosAtivos());
        modelView.addObject("produtoFilter", produtoFilter);
        
        return modelView;
    }
	
	@GetMapping("/cadastrar")
    public ModelAndView add(Produto produto) {
         
        ModelAndView modelView = new ModelAndView("produto/produtoCreate");
        modelView.addObject("produto", produto);
        
        return modelView;
    }
     
 
    @PostMapping("/cadastrar")
    public ModelAndView save(@Valid Produto produto, BindingResult result) {
    	
    	
    	if(produto.getValidade() != null && produtoService.verificarValidade(produto.getValidade())){
    		result.addError(new FieldError("produto", "validade", "Data inferior ao data atual"));
    	}
         
        if(result.hasErrors()) {
            return add(produto);
        }
         
        produtoService.adicionar(produto);
        
        return new ModelAndView("redirect:/sigem/produtos").addObject("sucesso", true);
    }
    
    @GetMapping("/detalhes/{id}")
    public ModelAndView details(@PathVariable("id") Long id) {
    	
    	ModelAndView mv = new ModelAndView("produto/produtoDetails");
        mv.addObject("produto", produtoService.buscarPorId(id));         
        return mv;
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
    	
    	ModelAndView mv = new ModelAndView("produto/produtoUpdate");
        mv.addObject("produto", produtoService.buscarPorId(id));
         
        return mv;
    }
    
    @PostMapping("/editar/{id}")
    public ModelAndView update(@Valid Produto produto, BindingResult result) {
         
    	
    	
        if(result.hasErrors()) {
        	ModelAndView mv = new ModelAndView("produto/produtoUpdate");
            mv.addObject("produto", produto);
            return mv;
        }
        
        produto.setAtivo(1);
        produtoService.atualizar(produto);
        return new ModelAndView("redirect:/sigem/produtos").addObject("atualizado", true);
    }
     
    @GetMapping("/remover/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
    	Produto produto = produtoService.buscarPorId(id);
    	if(produto != null) {
    		produto.setAtivo(0);
    		produtoService.atualizar(produto);
    	}
    	return new ModelAndView("redirect:/sigem/produtos").addObject("removido", true);
    }
 
    @PostMapping
    public ModelAndView pesquisar(ProdutoFilter produtoFiler){
    	ModelAndView mv = new ModelAndView("produto/index");
    	mv.addObject("produtoFiler", produtoFiler);
    	mv.addObject("produtos", produtoService.filtrar(produtoFiler));
    	
    	return mv;
    }

}
