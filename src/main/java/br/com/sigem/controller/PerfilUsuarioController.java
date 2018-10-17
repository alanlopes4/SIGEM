package br.com.sigem.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import br.com.sigem.model.PerfilUsuario;
import br.com.sigem.model.Usuario;
import br.com.sigem.repository.filter.PerfilUsuarioFilter;
import br.com.sigem.service.PerfilUsuarioService;
/**
 * Classe responsável pela interação com os requests vindos da web
 * @author Gustavo Mendes
 *
 */

@Controller
@RequestMapping("/sigem/perfilUsuarios")
public class PerfilUsuarioController {

	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
   
	
	@GetMapping
    public ModelAndView findAll(@RequestParam("perfilUsuarioFilter") Optional<PerfilUsuarioFilter> perfilUsuarioFilter) {
        ModelAndView mv = new ModelAndView("perfilUsuario/index");
        
        mv.addObject("perfilUsuarioFilter", perfilUsuarioFilter);
        
        mv.addObject("perfilUsuarios", perfilUsuarioService.listarTodosAtivos());
        return mv;
    }
	
     
    @GetMapping("/cadastrar")
    public ModelAndView add(PerfilUsuario perfilUsuario) {
        ModelAndView mv = new ModelAndView("perfilUsuario/perfilUsuarioCreate");
        mv.addObject("perfilUsuario", perfilUsuario);
        return mv;
    }
    
    @PostMapping("/cadastrar")
    public ModelAndView save(@Valid PerfilUsuario perfilUsuario, BindingResult result) {
    	
    	
        if(result.hasErrors()) {
            return  add(perfilUsuario);
        }
         
        perfilUsuarioService.adicionar(perfilUsuario);
        return new ModelAndView("redirect:/sigem/perfilUsuarios").addObject("sucesso", true);
    }
     
    @GetMapping("/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
    	
    	ModelAndView mv = new ModelAndView("perfilUsuario/perfilUsuarioUpdate");
        mv.addObject("perfilUsuario", perfilUsuarioService.buscarPorId(id));
         
        return mv;
    }
    
    @PostMapping("/editar/{id}")
    public ModelAndView update(@Valid PerfilUsuario perfilUsuario, BindingResult result) {
        
    	
    	
        if(result.hasErrors()) {
        	ModelAndView mv = new ModelAndView("perfilUsuario/perfilUsuarioUpdate");
            mv.addObject("perfilUsuario", perfilUsuario);
            return mv;
        }
         
        perfilUsuarioService.atualizar(perfilUsuario);
        return new ModelAndView("redirect:/sigem/perfilUsuarios").addObject("atualizado", true);
    }
     /**
      * Método utilizado para excluir um perfil de usuário
      * Exclusão Lógica
      * @param id
      * @return
      */
    @GetMapping("/remover/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
    	PerfilUsuario perfilUsuario = perfilUsuarioService.buscarPorId(id);
    	if(perfilUsuario != null) {
    		perfilUsuario.setAtivo(0);
    		perfilUsuarioService.atualizar(perfilUsuario);
    	}
    	return new ModelAndView("redirect:/sigem/perfilUsuarios").addObject("removido", true);
    }
 
    @PostMapping 
    public ModelAndView pesquisar(PerfilUsuarioFilter perfilUsuarioFilter){
    	ModelAndView mv = new ModelAndView("perfilUsuario/index");
    	mv.addObject("perfilUsuariofilter", perfilUsuarioFilter);
        List<PerfilUsuario>perfis=perfilUsuarioService.filtrar(perfilUsuarioFilter);
        if (perfis != null && perfis.size()==0){
            mv.addObject("naoencontrado", true);
        }else {
            mv.addObject("perfilUsuarios",perfis);
        }
    	
        
    	return mv;
    }
	 
    
	
}
