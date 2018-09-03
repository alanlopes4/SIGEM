package br.com.sigem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sigem.model.Estoque;
import br.com.sigem.repository.estoque.EstoqueRepositoryQuery;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long>, EstoqueRepositoryQuery{

	
}
