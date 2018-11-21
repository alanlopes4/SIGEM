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
	
		Object resultadoEstoque[] = atualizarEstoque(estoque.getEstoque(), estoque.getAdicionadaGondola(), estoque.getEstoqueMinimo());
		Object resultadoGondola[] = atualizarGondola(estoque.getGondola(), estoque.getAdicionadaGondola(), estoque.getEstoque());
			
			
			estoque.setMensagem((String) resultadoGondola[0]);
			estoque.setEstoque((int) resultadoEstoque[1]);
			estoque.setGondola((int) resultadoGondola[1]);
			
			
			
		    if(estoque.getMensagem().equals("")) 
		    	estoque.setMensagem("");
				
		   
			
		
	        return estoqueRepository.saveAndFlush(estoque);
	 }
	
	public Object[] atualizarEstoque(int quantidade_atual_estoque, int quantidade_retirada, int quantidade_minima) {
		
		String mensagem = "";
		int resultado =quantidade_atual_estoque;
		if (quantidade_atual_estoque == 0)
			mensagem = "O estoque está vazio";
		else if(quantidade_retirada < 0)
			mensagem = "Quantidade a se retirar inválida";
		else if(quantidade_atual_estoque < 0)
			mensagem = "Quantidade no estoque inválida";
		else if(quantidade_minima < 0)
			mensagem = "Quantidade minima no estoque inválida";	
		else if(quantidade_retirada > quantidade_atual_estoque)
			mensagem = "Quantidade a retirar maior do que a quantidade disponível no estoque";
		else
			resultado = quantidade_atual_estoque - quantidade_retirada;
		
		if(mensagem.equals("") && resultado < quantidade_minima)
			mensagem ="É necessário abastecer o estoque";
		
		
		Object[] obj = new Object[2];
		obj[0] = mensagem;
		obj[1] = resultado;
		
		return obj;
		
	}
	
	public Object[]  atualizarGondola(int quantidade_atual_gondola, int quantidade_adicionada, int quantidade_maxima) {
		String mensagem = "";
		int resultado = quantidade_atual_gondola;
			if(quantidade_atual_gondola == 0 && quantidade_adicionada == 0)
				mensagem = "Abastecer gôndola";
			else if(quantidade_adicionada < 0)
				mensagem = "Quantidade a adicionar inválida";
			else if(quantidade_atual_gondola + quantidade_adicionada > quantidade_maxima)
				mensagem = "Quantidade adicionada superior ao máximo suportado";
			else
				resultado = quantidade_atual_gondola + quantidade_adicionada;
			
			Object[] obj = new Object[2];
			obj[0] = mensagem;
			obj[1] = resultado;
			
		return obj;
		
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
