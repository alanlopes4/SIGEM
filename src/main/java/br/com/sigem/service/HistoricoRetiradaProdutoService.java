package br.com.sigem.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.HistoricoRetiradaProduto;
import br.com.sigem.repository.HistoricoRetiradaProdutoRepository;

@Service
public class HistoricoRetiradaProdutoService {
	
	@Autowired
	private HistoricoRetiradaProdutoRepository historicoRetiradaProdutoRepository;
	
	
	public void adicionar(HistoricoRetiradaProduto historicoPreco) {
		historicoRetiradaProdutoRepository.saveAndFlush(historicoPreco);
	}
	
	public void atualizar(HistoricoRetiradaProduto historicoPreco) {
		historicoRetiradaProdutoRepository.saveAndFlush(historicoPreco);
	}
	
	public List<HistoricoRetiradaProduto> filtrar(LocalDate dataInicio, LocalDate dataFim){
		
		return historicoRetiradaProdutoRepository.filtrar(dataInicio, dataFim);
	}

	public HistoricoRetiradaProduto filtrarPorProduto(Produto produto) {
		//return historicoRetiradaProdutoRepository.findFirstByProdutoOrderByDataRetiradaDesc(produto);
		return null;
	}
	
	public List<HistoricoRetiradaProduto> listarTodos(){
		return historicoRetiradaProdutoRepository.findAll();
	}

}
