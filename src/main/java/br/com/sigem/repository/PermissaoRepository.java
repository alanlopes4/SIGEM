package br.com.sigem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sigem.model.Permissao;
import br.com.sigem.model.Usuario;
import br.com.sigem.repository.permissao.PermissaoRepositoryQuery;

@Repository
public interface PermissaoRepository extends JpaRepository<Usuario, Long>, PermissaoRepositoryQuery{
	
	public List<Permissao> findByNome(String nome);

}
