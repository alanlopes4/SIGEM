package br.com.sigem.repository.historicoPreco;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.HistoricoPreco;

public class HistoricoPrecoRepositoryImpl implements HistoricoPrecoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<HistoricoPreco> filtrar(LocalDate dataInicio, LocalDate dataFim) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<HistoricoPreco> criteria = builder.createQuery(HistoricoPreco.class);
		
		Root<HistoricoPreco> root = criteria.from(HistoricoPreco.class);
		
		Predicate[] predicates = criarRestricoes(dataInicio, dataFim, builder, root);
		criteria.where(predicates);
		
		TypedQuery<HistoricoPreco> query = manager.createQuery(criteria);
				
		return query.getResultList(); 
	}

	
	@Override
	public HistoricoPreco filtrarPorProduto(Produto produto) {
		
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<HistoricoPreco> criteria = builder.createQuery(HistoricoPreco.class);
		
		Root<HistoricoPreco> root = criteria.from(HistoricoPreco.class);
		
		Predicate[] predicates = criarRestricoesFiltrarPorProduto(produto, builder, root);
		criteria.where(predicates);
		
		TypedQuery<HistoricoPreco> query = manager.createQuery(criteria);
				
		
		return query.getResultList().get(0); 
		
	}
	
	
	private Predicate[] criarRestricoes(LocalDate dataInicio, LocalDate dataFim, CriteriaBuilder builder, Root<HistoricoPreco> root) {
		List<Predicate> predicates	= new ArrayList<>();

		if(dataInicio != null && dataFim != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataAlteracao"), dataInicio));
			predicates.add(builder.lessThanOrEqualTo(root.get("dataAlteracao"), dataFim));
		}
		
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private Predicate[] criarRestricoesFiltrarPorProduto(Produto produto, CriteriaBuilder builder, Root<HistoricoPreco> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		if(produto != null) 
			predicates.add(builder.equal(root.get("produto"), produto));
		
		//predicates.add(builder.max(root.get("dataAlteracao")));
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}


	@Override
	public List<Object[]> filtrarGrafico(LocalDate dataInicio, LocalDate dataFim) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<HistoricoPreco> criteria = builder.createQuery(HistoricoPreco.class);
		
		Root<HistoricoPreco> root = criteria.from(HistoricoPreco.class);
		
		
		
		Predicate[] predicates = criarRestricoesGrafico(dataInicio, dataFim, builder, root);
		criteria.where(predicates);
		Query query = manager.createNativeQuery("SELECT p.nome , COUNT(hp.produto_id), hp.data_alteracao" + 
				"	FROM produto p INNER JOIN historico_preco hp" + 
				"			ON p.id = hp.produto_id" + 
				"	GROUP BY produto_id HAVING COUNT(hp.produto_id)>0" + 
				"	ORDER BY COUNT(hp.produto_id) ASC");
		List<Object[]> dados = query.getResultList();
				
		return dados;
	}
	
	private Predicate[] criarRestricoesGrafico(LocalDate dataInicio, LocalDate dataFim, CriteriaBuilder builder, Root<HistoricoPreco> root) {
		List<Predicate> predicates	= new ArrayList<>();
 		if(dataInicio != null && dataFim != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataAlteracao"), dataInicio));
			predicates.add(builder.lessThanOrEqualTo(root.get("dataAlteracao"), dataFim));
		}
		
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
}
