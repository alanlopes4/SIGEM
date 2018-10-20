package br.com.sigem.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigem.model.Estoque;
import br.com.sigem.model.Produto;
import br.com.sigem.repository.EstoqueRepository;
import br.com.sigem.repository.filter.EstoqueFilter;

@Service
public class EstoqueService {
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	public Estoque adicionar(@Valid Produto produto) {
		
		Estoque estoque = new Estoque();
		estoque.setProduto(produto);
		estoque.setEstoqueMinimo(produto.getQuantidadeMinima());
		estoque.setGondola(0);
		estoque.setEstoque(produto.getQuantidade());
		estoqueRepository.saveAndFlush(estoque);
		
		return estoque;
	}
	
	public List<Estoque> listaTodos() {
		return estoqueRepository.findAll();
	}
	
	public Estoque buscarPorId(Long id) {
        return estoqueRepository.findById(id).orElse(null);
    }
	
	
	public Estoque atualizar(@Valid Estoque estoque) {
			
			estoque.setEstoque(estoque.getEstoque() - estoque.getGondola());
		
	        return estoqueRepository.saveAndFlush(estoque);
	   }
	
	    
	public List<Estoque> filtrar(EstoqueFilter estoqueFilter){
	    	return estoqueRepository.filtrar(estoqueFilter);
	   }

}
