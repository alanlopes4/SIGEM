package br.com.sigem.repository.EntradaProduto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.EntradaProduto;

public class EntradaProdutoRepositoryImpl implements EntradaProdutoRepositoryQuery{
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<EntradaProduto> filtrar(LocalDate dataInicio, LocalDate dataFim) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<EntradaProduto> criteria = builder.createQuery(EntradaProduto.class);
		
		Root<EntradaProduto> root = criteria.from(EntradaProduto.class);
		
		Predicate[] predicates = criarRestricoes(dataInicio, dataFim, builder, root);
		criteria.where(predicates);
		
		TypedQuery<EntradaProduto> query = manager.createQuery(criteria);
				
		return query.getResultList(); 
	}

	@Override
	public EntradaProduto filtrarPorProduto(Produto produto) {
		
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<EntradaProduto> criteria = builder.createQuery(EntradaProduto.class);
		
		Root<EntradaProduto> root = criteria.from(EntradaProduto.class);
		
		Predicate[] predicates = criarRestricoesFiltrarPorProduto(produto, builder, root);
		criteria.where(predicates);
		
		TypedQuery<EntradaProduto> query = manager.createQuery(criteria);
				
		
		return query.getResultList().get(0); 
		
	}

	private Predicate[] criarRestricoes(LocalDate dataInicio, LocalDate dataFim, CriteriaBuilder builder, Root<EntradaProduto> root) {
		List<Predicate> predicates	= new ArrayList<>();

		if(dataInicio != null && dataFim != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCompra"), dataInicio));
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCompra"), dataFim));
		}
		
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private Predicate[] criarRestricoesFiltrarPorProduto(Produto produto, CriteriaBuilder builder, Root<EntradaProduto> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		if(produto != null) 
			predicates.add(builder.equal(root.get("produto"), produto));
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
