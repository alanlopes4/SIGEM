package br.com.sigem.repository.estoque;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.sigem.model.Estoque;
import br.com.sigem.repository.filter.EstoqueFilter;

public class EstoqueRepositoryImpl implements EstoqueRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Estoque> filtrar(EstoqueFilter estoqueFilter) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<Estoque> criteria = builder.createQuery(Estoque.class);
		
		Root<Estoque> root = criteria.from(Estoque.class);
		
		Predicate[] predicates = criarRestricoes(estoqueFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Estoque> query = manager.createQuery(criteria);
		
		//adicionarRestricoesDePaginacao(query, pageable);
		
		return query.getResultList(); 
	}

	private Predicate[] criarRestricoes(EstoqueFilter estoqueFilter, CriteriaBuilder builder, Root<Estoque> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		if(!StringUtils.isEmpty(estoqueFilter.getNome()))
			predicates.add(builder.like(
					builder.lower(root.get("nome")), 
					"%"+ estoqueFilter.getNome().toLowerCase()+ "%"));
		if(estoqueFilter.getEstoque() != null && estoqueFilter.getEstoque() > 0)
			predicates.add(builder.equal(root.get("estoque"), estoqueFilter.getEstoque()));
		if(estoqueFilter.getGondula() != null && estoqueFilter.getGondula() > 0)
			predicates.add(builder.equal(root.get("gondola"), estoqueFilter.getGondula()));
		if(estoqueFilter.getEstoqueMinimo() != null && estoqueFilter.getEstoqueMinimo() > 0)
			predicates.add(builder.equal(root.get("estoqueMinimo"), estoqueFilter.getEstoqueMinimo()));
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
}
