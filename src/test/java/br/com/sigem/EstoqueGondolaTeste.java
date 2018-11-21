package br.com.sigem;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sigem.service.EstoqueService;
/**
 * Classe de testes para a funcionalidade de atualização do estoque
 * @author Alan Lopes de Sousa Freitas
 *
 */
public class EstoqueGondolaTeste extends SigemApplicationTests{

	@Autowired
	private EstoqueService estoqueService;
	
	@Test
	public void atualizarEstoque_ct1() {
		Object resultado[] = estoqueService.atualizarEstoque(100, 20, 10);
		
		Assertions.assertThat(resultado[0]).isEqualTo("");
		Assertions.assertThat((int)resultado[1] == 80);
	}
	
	@Test
	public void atualizarEstoque_ct2() {
		Object resultado[] = estoqueService.atualizarEstoque(15, 20, 10);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Quantidade a retirar maior do");
		Assertions.assertThat((int)resultado[1] == 15);
		
	}
	@Test
	public void atualizarEstoque_ct3() {
		Object resultado[] = estoqueService.atualizarEstoque(20, 20, 10);
		
		Assertions.assertThat(resultado[0]).isEqualTo("É necessário abastecer o estoque");
		Assertions.assertThat((int)resultado[1] == 0);
		
	}
	
	@Test
	public void atualizarEstoque_ct4() {
		Object resultado[] = estoqueService.atualizarEstoque(0, 20, 10);
		
		Assertions.assertThat(resultado[0]).isEqualTo("O estoque está vazio");
		Assertions.assertThat((int)resultado[1] == 0);
		
	}
	@Test
	public void atualizarEstoque_ct5() {
		Object resultado[] = estoqueService.atualizarEstoque(100, -20, 10);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Quantidade a se retirar inválida");
		Assertions.assertThat((int)resultado[1] == 100);
		
	}
	@Test
	public void atualizarEstoque_ct6() {
		Object resultado[] = estoqueService.atualizarEstoque(-10, 20, 10);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Quantidade no estoque inválida");
		
	}
	
	@Test
	public void atualizarEstoque_ct7() {
		Object resultado[] = estoqueService.atualizarEstoque(100, 20, -10);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Quantidade minima no estoque inválida");
		
	}
	
}
