package br.com.sigem;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sigem.service.EstoqueService;

public class EstoqueGondolaTeste2 extends SigemApplicationTests{

	@Autowired
	private EstoqueService estoqueService;
	
	@Test
	public void atualizarGondola_t1() {
		Object resultado[] = estoqueService.atualizarGondola(100, 20, 200);
		
		Assertions.assertThat(resultado[0]).isEqualTo("");
		Assertions.assertThat((int)resultado[1] == 120);
	}
	
	@Test
	public void atualizarGondola_t2() {
		Object resultado[] = estoqueService.atualizarGondola(0, 0, 25);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Abastecer gôndola");
		Assertions.assertThat((int)resultado[1] == -2);
		
	}
	@Test
	public void atualizarGondola_t3() {
		Object resultado[] = estoqueService.atualizarGondola(25, -2, 25);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Quantidade a adicionar inválida");
		Assertions.assertThat((int)resultado[1] == 25);
		
	}
	
	@Test
	public void atualizarGondola_t4() {
		Object resultado[] = estoqueService.atualizarGondola(0, 20, 10);
		
		Assertions.assertThat(resultado[0]).isEqualTo("Quantidade adicionada superior ao máximo suportado");
		Assertions.assertThat((int)resultado[1] == 0);
		
	}
}