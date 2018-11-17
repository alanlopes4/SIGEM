package br.com.sigem.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigem.model.Estoque;
import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.HistoricoRetiradaProduto;
import br.com.sigem.repository.EstoqueRepository;
import br.com.sigem.repository.filter.EstoqueFilter;

@Service
public class EstoqueService {
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private HistoricoRetiradaProdutoService historicoRetiradaProdutoService;
	
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
	
		int quantidade_estoque = atualizarEstoque(estoque.getEstoque(), estoque.getAdicionadaGondola(), estoque.getEstoqueMinimo());
		int quantidade_gondola = atualizarGondola(estoque.getGondola(), estoque.getAdicionadaGondola());
			if(quantidade_estoque == -1)
				estoque.setMensagem("Quantidade a retirar maior do que a quantidade disponível no estoque");
			else if(quantidade_estoque == -2)
				estoque.setMensagem("O estoque está vazio");
			else if(quantidade_estoque == -3)
				estoque.setMensagem("Quantidade a se retirar inválida");
			else if(quantidade_estoque == -4)
				estoque.setMensagem("Quantidade no estoque inválida");
			else if(quantidade_estoque == -5)
				estoque.setMensagem("Quantidade minima no estoque inválida");
			else if(quantidade_estoque < estoque.getEstoqueMinimo()) {
				estoque.setMensagem("É necessário abastecer o estoque");
				estoque.setEstoque(quantidade_estoque);
			}
			else {
				estoque.setMensagem("");
				estoque.setEstoque(quantidade_estoque);
			}
				
		
		
			if(quantidade_gondola == -1)
				estoque.setMensagem("Quantidade na gôndola inválida");
			else if(quantidade_gondola == -2)
				estoque.setMensagem("Quantidade a adicionar inválida");
			else {
		    if(estoque.getMensagem().equals("")) estoque.setMensagem("");
			estoque.setGondola(quantidade_gondola);
			}
		
	        return estoqueRepository.saveAndFlush(estoque);
	 }
	
	private int atualizarEstoque(int quantidade_atual_estoque, int quantidade_retirada, int quantidade_minima) {
		
		int resultado = 0;
		if(quantidade_retirada > quantidade_atual_estoque)
			resultado = -1;
		else if (quantidade_atual_estoque == 0)
			resultado = -2;
		else if(quantidade_retirada < 0)
			resultado = -3;
		else if(quantidade_atual_estoque < 0)
			resultado = -4;
		else if(quantidade_minima < 0)
			resultado = -5;
		else
			resultado =  quantidade_atual_estoque - quantidade_retirada;
		
		return resultado;
		
	}
	
	private int atualizarGondola(int quantidade_atual_gondola, int quantidade_adicionada) {
		int resultado = 0;
			if(quantidade_atual_gondola < 0)
				resultado = -1;
			else if(quantidade_adicionada < 0)
				resultado = -2;
			else
				resultado = quantidade_atual_gondola + quantidade_adicionada;
		return resultado;
		
	}
	
	public Estoque retirar(@Valid Estoque estoque) {
		
		estoque.setGondola(estoque.getGondola() - estoque.getRetiradoGondola());
		historicoRetiradaProdutoService.adicionar(new HistoricoRetiradaProduto(estoque.getProduto(), LocalDate.now(), estoque.getMotivo(), estoque.getRetiradoGondola()));
		
		return estoqueRepository.saveAndFlush(estoque);
	}
	
	    
	public List<Estoque> filtrar(EstoqueFilter estoqueFilter){
	    	return estoqueRepository.filtrar(estoqueFilter);
	   }

}
