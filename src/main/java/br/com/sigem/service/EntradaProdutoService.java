package br.com.sigem.service;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigem.model.relatorio.EntradaProduto;
import br.com.sigem.repository.EntradaProdutoRepository;

@Service
public class EntradaProdutoService {
	
	@Autowired
	private EntradaProdutoRepository entradaProdutoRepository;
	
	public void adicionar(EntradaProduto entradaProduto) {
		entradaProdutoRepository.saveAndFlush(entradaProduto);
	}
	
	public void atualizar(EntradaProduto entradaProduto) {
		entradaProdutoRepository.saveAndFlush(entradaProduto);
	}
	
	public List<EntradaProduto> filtrar(LocalDate dataInicio, LocalDate dataFim){
		return entradaProdutoRepository.filtrar(dataInicio, dataFim);
	}
	
	public List<EntradaProduto> listarTodos(){
		return entradaProdutoRepository.findAll();
	}
	
	public List<Object[]> filtrarGrafico(LocalDate dataInicio, LocalDate dataFim){
		
		return entradaProdutoRepository.filtrarGrafico(dataInicio, dataFim);
	}
}
