package br.com.sigem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.BaseRelatorioFactory;
import br.com.sigem.model.relatorio.EntradaProduto;
import br.com.sigem.model.relatorio.HistoricoPreco;
import br.com.sigem.model.relatorio.Relatorio;
import br.com.sigem.model.relatorio.RelatorioFactory;
import br.com.sigem.repository.ProdutoRepository;
import br.com.sigem.repository.filter.ProdutoFilter;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private HistoricoPrecoService historicoPrecoService;
	@Autowired
	private EntradaProdutoService entradaProdutoService;
	@Autowired
	private EstoqueService estoqueService;
	
	
	public List<Produto> listaTodos() {
		return produtoRepository.findAll();
	}
	
	public List<Produto> listarTodosAtivos(){
		return produtoRepository.findByAtivo(1);
	}
	
	public List<Produto> findByLote(String lote){
		return produtoRepository.findByLote(lote);
	}
	
	public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }
	
	 public Produto adicionar(@Valid Produto produto) {
		 	produto.setAtivo(1);
	        Produto p = produtoRepository.saveAndFlush(produto);
	        if(p != null) {
	        	Optional<HistoricoPreco> hp = null;
	        	BaseRelatorioFactory relatorioFactory = new RelatorioFactory();
	        	Relatorio historicoPreco = relatorioFactory.criarRelatorio("historicoPrecoNovo", p, hp);
	        	Relatorio entradaProduto = relatorioFactory.criarRelatorio("entradaProduto", p, hp);
	        	historicoPrecoService.adicionar((HistoricoPreco)historicoPreco);
	        	entradaProdutoService.adicionar((EntradaProduto)entradaProduto);
	        	estoqueService.adicionar(p);
	        }
	        return p;
	    }
	    
	 
	public Produto atualizar(@Valid Produto produto) {
	        Produto p =  produtoRepository.saveAndFlush(produto);
	        if(p!=null) {
	        	Optional<HistoricoPreco> hp =  Optional.ofNullable(historicoPrecoService.filtrarPorProduto(p));
	        	if(hp != null) {
	        		BaseRelatorioFactory relatorioFactory = new RelatorioFactory();
	        		Relatorio historicoPreco = relatorioFactory.criarRelatorio("historicoPrecoAtualizado", p, hp);
		        	historicoPrecoService.adicionar((HistoricoPreco)historicoPreco);
	        	}
	        }
	        return p;
	   }
	
	public void remover(Long id) {
	    	Produto produto = buscarPorId(id);
	    	produtoRepository.delete(produto);
	   }
	    
	    
	public List<Produto> filtrar(ProdutoFilter produtoFilter){
	    	return produtoRepository.filtrar(produtoFilter);
	   }
	
	public List<Produto> filtrarValidade(ProdutoFilter produtoFilter){
    	return produtoRepository.filtrarValidade(produtoFilter);
   }
	public boolean verificarValidade(LocalDate validade) {
		return validade.isBefore(LocalDate.now());
	}

}
