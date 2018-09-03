package br.com.sigem.controller;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.sigem.model.Permissao;
import br.com.sigem.model.Usuario;
import br.com.sigem.repository.filter.UsuarioFilter;
import br.com.sigem.service.PerfilUsuarioService;
import br.com.sigem.service.PermissaoService;
import br.com.sigem.service.UsuarioService;


/**
 * Classe responsável pela interação com os requests vindos da web
 * @author Alan Lopes
 *
 */
@Controller
@RequestMapping("/sigem/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	@Autowired
	private PermissaoService permissaoService;
   
	
	@GetMapping
    public ModelAndView findAll(@RequestParam("usuarioFilter") Optional<UsuarioFilter> usuarioFilter) {
        ModelAndView mv = new ModelAndView("usuario/index");
        
        mv.addObject("usuarioFilter", usuarioFilter);
        
        
        mv.addObject("usuarios", usuarioService.listarTodosAtivos());
        return mv;
    }
	
     
    @GetMapping("/cadastrar")
    public ModelAndView add(Usuario usuario) {
        ModelAndView mv = new ModelAndView("usuario/usuarioCreate");
        mv.addObject("usuario", usuario);
        mv.addObject("perfisUsuario", perfilUsuarioService.listarTodosAtivos());
        return mv;
    }
    
    @PostMapping("/cadastrar")
    public ModelAndView save(@Valid Usuario usuario, BindingResult result) {
    	
    	if(!(usuario.getSenha() == null && usuario.getConfirmarSenha() == null || 
    			usuario.getSenha() != null && usuario.getSenha().equals(usuario.getConfirmarSenha()))){
    		result.addError(new FieldError("usuario", "confirmarSenha", "As senhas digitadas não correspondem"));
    	}
    	if(!(usuario.getEmail() == null && usuario.getConfirmarEmail() == null || 
    			usuario.getEmail() != null && usuario.getEmail().equals(usuario.getConfirmarEmail()))){
    		result.addError(new FieldError("usuario", "confirmarEmail", "Os emails digitados não correspondem"));
    	}
        
    	
    	
        if(result.hasErrors()) {
            return  add(usuario);
        }
       
        Set<Permissao> permissoes = new HashSet<>();
        for(String permissao :usuario.getPerfilUsuario().getPermissoes()) {
        	Permissao p = permissaoService.buscarPorNome(permissao);
        	if(p != null) 
        		permissoes.add(p);
        }
        if(!permissoes.isEmpty())
        	usuario.setPermissoes(permissoes);
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        usuario.setAtivo(1);
        usuarioService.adicionar(usuario);
        
        return new ModelAndView("redirect:/sigem/usuarios").addObject("sucesso", true);
    }
     
    @GetMapping("/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
    	
    	ModelAndView mv = new ModelAndView("usuario/usuarioUpdate");
        mv.addObject("usuario", usuarioService.buscarPorId(id));
        mv.addObject("perfisUsuario", perfilUsuarioService.listarTodosAtivos());
         
        return mv;
    }
    
    @PostMapping("/editar/{id}")
    public ModelAndView update(@Valid Usuario usuario, BindingResult result) {
         
    	if(!(usuario.getSenha() == null && usuario.getConfirmarSenha() == null || 
    			usuario.getSenha() != null && usuario.getSenha().equals(usuario.getConfirmarSenha()))){
    		result.addError(new FieldError("usuario", "confirmarSenha", "As senhas digitadas não correspondem"));
    	}
    	if(!(usuario.getEmail() == null && usuario.getConfirmarEmail() == null || 
    			usuario.getEmail() != null && usuario.getEmail().equals(usuario.getConfirmarEmail()))){
    		result.addError(new FieldError("usuario", "confirmarEmail", "Os emails digitados não correspondem"));
    	}
    	
    	
        if(result.hasErrors()) {
        	ModelAndView mv = new ModelAndView("usuario/usuarioUpdate");
            mv.addObject("usuario", usuario);
            mv.addObject("perfisUsuario", perfilUsuarioService.listarTodosAtivos());
            return mv;
        }
        Set<Permissao> permissoes = new HashSet<>();
        for(String permissao :usuario.getPerfilUsuario().getPermissoes()) {
        	Permissao p = permissaoService.buscarPorNome(permissao);
        	if(p != null) 
        		permissoes.add(p);
        }
        if(!permissoes.isEmpty())
        	usuario.setPermissoes(permissoes);
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        usuario.setAtivo(1);
        usuarioService.atualizar(usuario);
        return new ModelAndView("redirect:/sigem/usuarios").addObject("atualizado", true);
    }
     
    @GetMapping("/remover/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
    	
    	Usuario usuario = usuarioService.buscarPorId(id);
    	if(usuario != null) {
    		usuario.setAtivo(0);
    		usuarioService.atualizar(usuario);
    	}
    	return new ModelAndView("redirect:/sigem/usuarios").addObject("removido", true); 
    }
 
    @PostMapping
    public ModelAndView pesquisar(UsuarioFilter usuarioFilter){
    	ModelAndView mv = new ModelAndView("usuario/index");
    	mv.addObject("usuariofilter", usuarioFilter);
    	mv.addObject("usuarios", usuarioService.filtrar(usuarioFilter));
    	
    	return mv;
    }
	
	
}
