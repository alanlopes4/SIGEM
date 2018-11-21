package br.com.sigem;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sigem.model.Produto;
import br.com.sigem.repository.filter.ProdutoFilter;
import br.com.sigem.service.ProdutoService;

public class ProdutoTeste extends SigemApplicationTests{
	
	@Autowired
	private ProdutoService produtoService;
	
	@Test
	public void listaTodos() {
		List<Produto> produtos = produtoService.listaTodos();
		Assertions.assertThat(produtos.size()).isGreaterThan(0);
	}
	
	@Test
	public void listarTodosAtivos() {
		List<Produto> produtos = produtoService.listarTodosAtivos();
		Assertions.assertThat(produtos.size()).isGreaterThan(0);
	}
	
	@Test
	public void findByLote() {
		List<Produto> produtos = produtoService.findByLote("afsdgdsgds");
		Assertions.assertThat(produtos.size()).isGreaterThan(0);
	}
	
	@Test
	public void buscarPorId() {
		Produto produto = produtoService.buscarPorId(1L);
		Assertions.assertThat(produto.getId()).isEqualTo(1L);
	}
	
	@Test
	public void adicionar() {
		LocalDate validade = LocalDate.parse("2018-11-29");
		LocalDate dataCompra = LocalDate.parse("2018-05-03");
		Produto produto = new Produto(1L, "Teste", "gdsg1g16sd", "Marca Teste", 20, 15, 2.5, 250.5, dataCompra, validade, true, "Teste");
		produto.setCodigo("123456789");
		Produto p = produtoService.adicionar(produto);
		Assertions.assertThat(p.getMarca()).isEqualTo("Marca Teste");
	}
	
	@Test
	public void atualizar() {
		
		Produto produto = produtoService.buscarPorId(1L);
		produto.setMarca("Marca Teste 2");
		Produto produtoUpdated = produtoService.atualizar(produto);
		Assertions.assertThat(produtoUpdated.getMarca()).isEqualTo("Marca Teste 2");
	}
	
	@Test
	public void remover() {
		produtoService.remover(1L);
		Produto produto = produtoService.buscarPorId(1L);
		Assertions.assertThat(produto.isAtivo()).isEqualTo(0);
	}
	
	@Test
	public void filtrar() {
		ProdutoFilter produtoFilter = new ProdutoFilter();
		produtoFilter.setMarca("Marca Teste");
		List<Produto> produtosFiltered = produtoService.filtrar(produtoFilter);
		Assertions.assertThat(produtosFiltered.size()).isGreaterThan(0);
	}
	
	@Test
	public void filtrarValidade() {
		ProdutoFilter produtoFilter = new ProdutoFilter();
		LocalDate validade = LocalDate.parse("2018-11-30");
		produtoFilter.setValidade(validade);
		List<Produto> produtosFiltered = produtoService.filtrarValidade(produtoFilter);
		Assertions.assertThat(produtosFiltered.size()).isGreaterThan(0);
	}
	
	@Test
	public void verificarValidade() {
		LocalDate validade = LocalDate.parse("2019-01-01");
		Boolean produto = produtoService.verificarValidade(validade);
		Assertions.assertThat(produto).isEqualTo(false);
	}
	

}