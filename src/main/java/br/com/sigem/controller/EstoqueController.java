package br.com.sigem.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sigem.model.Estoque;
import br.com.sigem.repository.filter.EstoqueFilter;
import br.com.sigem.repository.filter.ProdutoFilter;
import br.com.sigem.service.EstoqueService;

@Controller
@RequestMapping("/sigem/estoque")
public class EstoqueController {
	@Autowired
	private EstoqueService estoqueService;
	
	@GetMapping
    public ModelAndView findAll(Optional<EstoqueFilter> estoqueFilter) {
		
        ModelAndView modelView = new ModelAndView("estoque/index");
        modelView.addObject("estoques", estoqueService.listaTodos());
        modelView.addObject("estoqueFilter", estoqueFilter);
        
        return modelView;
    }
    
	@PostMapping
    public ModelAndView pesquisar(EstoqueFilter estoqueFilter){
    	ModelAndView mv = new ModelAndView("estoque/index");
    	mv.addObject("estoqueFilter", estoqueFilter);
    	mv.addObject("estoques", estoqueService.filtrar(estoqueFilter));
    	
    	return mv;
    }
	
	
    @PostMapping("/editar/{id}")
    public ModelAndView update(@Valid Estoque estoque, BindingResult result) {
         
    	
    	
        if(result.hasErrors()) {
        	ModelAndView mv = new ModelAndView("estoque/estoqueUpdate");
            mv.addObject("estoque", estoque);
            return mv;
        }
         
        estoqueService.atualizar(estoque);
        return new ModelAndView("redirect:/sigem/estoque");
    }
     
}
