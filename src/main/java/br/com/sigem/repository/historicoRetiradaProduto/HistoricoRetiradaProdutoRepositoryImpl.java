package br.com.sigem.repository.historicoRetiradaProduto;

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
import br.com.sigem.model.relatorio.HistoricoRetiradaProduto;

public class HistoricoRetiradaProdutoRepositoryImpl implements HistoricoRetiradaProdutoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<HistoricoRetiradaProduto> filtrar(LocalDate dataInicio, LocalDate dataFim) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<HistoricoRetiradaProduto> criteria = builder.createQuery(HistoricoRetiradaProduto.class);
		
		Root<HistoricoRetiradaProduto> root = criteria.from(HistoricoRetiradaProduto.class);
		
		Predicate[] predicates = criarRestricoes(dataInicio, dataFim, builder, root);
		criteria.where(predicates);
		
		TypedQuery<HistoricoRetiradaProduto> query = manager.createQuery(criteria);
				
		return query.getResultList(); 
	}
	

	@Override
	public List<Object[]> filtrar(LocalDate dataInicio, LocalDate dataFim, String motivo) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<HistoricoRetiradaProduto> criteria = builder.createQuery(HistoricoRetiradaProduto.class);
		
		Root<HistoricoRetiradaProduto> root = criteria.from(HistoricoRetiradaProduto.class);
		
		
		
		Predicate[] predicates = criarRestricoes(dataInicio, dataFim, motivo, builder, root);
		criteria.where(predicates);
		Query query = manager.createNativeQuery("SELECT p.nome as nome, h.motivo as motivo, SUM(h.quantidade) as quantidade FROM "
				+ "historico_retirada_produto h INNER JOIN produto p ON p.id = h.produto_id "
				+ "WHERE h.data_retirada BETWEEN :dataInicio AND :dataFim AND h.motivo LIKE :motivo "
				+ "GROUP BY p.nome ORDER BY p.nome ASC");
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		query.setParameter("motivo", motivo);
		List<Object[]> dados = query.getResultList();
				
		return dados; 
	}

	
	@Override
	public HistoricoRetiradaProduto filtrarPorProduto(Produto produto) {
		
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<HistoricoRetiradaProduto> criteria = builder.createQuery(HistoricoRetiradaProduto.class);
		
		Root<HistoricoRetiradaProduto> root = criteria.from(HistoricoRetiradaProduto.class);
		
		Predicate[] predicates = criarRestricoesFiltrarPorProduto(produto, builder, root);
		criteria.where(predicates);
		
		TypedQuery<HistoricoRetiradaProduto> query = manager.createQuery(criteria);
				
		
		return query.getResultList().get(0); 
		
	}
	
	


	private Predicate[] criarRestricoes(LocalDate dataInicio, LocalDate dataFim, CriteriaBuilder builder, Root<HistoricoRetiradaProduto> root) {
		List<Predicate> predicates	= new ArrayList<>();

		if(dataInicio != null && dataFim != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataRetirada"), dataInicio));
			predicates.add(builder.lessThanOrEqualTo(root.get("dataRetirada"), dataFim));
		}
		
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private Predicate[] criarRestricoes(LocalDate dataInicio, LocalDate dataFim, String motivo, CriteriaBuilder builder, Root<HistoricoRetiradaProduto> root) {
		List<Predicate> predicates	= new ArrayList<>();

		if(dataInicio != null && dataFim != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataRetirada"), dataInicio));
			predicates.add(builder.lessThanOrEqualTo(root.get("dataRetirada"), dataFim));
			predicates.add(builder.equal(root.get("motivo"), motivo));
		}
		
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private Predicate[] criarRestricoesFiltrarPorProduto(Produto produto, CriteriaBuilder builder, Root<HistoricoRetiradaProduto> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		if(produto != null) 
			predicates.add(builder.equal(root.get("produto"), produto));
		
		//predicates.add(builder.max(root.get("dataAlteracao")));
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}


	
}
