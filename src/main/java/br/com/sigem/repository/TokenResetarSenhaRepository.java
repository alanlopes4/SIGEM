package br.com.sigem.repository;

import br.com.sigem.model.TokenResetarSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenResetarSenhaRepository extends JpaRepository<TokenResetarSenha, Long> {
	
	TokenResetarSenha findByToken(String token);
	
}
