package br.com.sigem.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.HistoricoPreco;
import br.com.sigem.repository.HistoricoPrecoRepository;

@Service
public class HistoricoPrecoService {
	
	@Autowired
	private HistoricoPrecoRepository historicoPrecoRepository;
	
	
	public void adicionar(HistoricoPreco historicoPreco) {
		historicoPrecoRepository.saveAndFlush(historicoPreco);
	}
	
	public void atualizar(HistoricoPreco historicoPreco) {
		historicoPrecoRepository.saveAndFlush(historicoPreco);
	}
	
	public List<HistoricoPreco> filtrar(LocalDate dataInicio, LocalDate dataFim){
		return historicoPrecoRepository.filtrar(dataInicio, dataFim);
	}
	
	public List<Object[]> filtrarGrafico(LocalDate dataInicio, LocalDate dataFim){
		
		return historicoPrecoRepository.filtrarGrafico(dataInicio, dataFim);
	}

	public HistoricoPreco filtrarPorProduto(Produto produto) {
		return historicoPrecoRepository.findFirstByProdutoOrderByDataAlteracaoDesc(produto);
	}
	
	public List<HistoricoPreco> listarTodos(){
		return historicoPrecoRepository.findAll();
	}

}
