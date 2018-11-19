package br.com.sigem.repository.estoque;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
		
		//Predicate[] predicates = criarRestricoes(estoqueFilter, builder, root);
		//criteria.where(predicates);
				
		String sql = "SELECT * FROM estoque e INNER JOIN produto p ON p.id = e.produto_id ";
		String where = "";
		boolean pred = false;
		
		if(!estoqueFilter.getProduto().getNome().equals("")) {
			where += " p.nome LIKE :nome";
			pred = true;
		}
		if(estoqueFilter.getEstoque()!= null)
			if(pred) {
				where += " AND e.estoque = :estoque";
				pred = true;
			}
			else {
				where += " e.estoque = :estoque";
				pred = true;
			}
		
		if(estoqueFilter.getEstoqueMinimo()!= null)
			if(pred) {
				where += " AND e.estoque_minimo = :estoque_minimo";
				pred = true;
			}
			else {
				where += " e.estoque_minimo = :estoque_minimo";
				pred = true;
			}
		
		if(estoqueFilter.getGondola()!= null)
			if(pred) {
				where += " AND e.gondola = :gondola";
				pred = true;
			}
			else {
				where += " e.gondola = :gondola";
				pred = true;
			}
		
		if(pred)
			where = "WHERE "+where;
		
		sql = sql+where;
		Query query = manager.createNativeQuery(sql, Estoque.class);
		
		if(!estoqueFilter.getProduto().getNome().equals(""))
			query.setParameter("nome", "%"+estoqueFilter.getProduto().getNome()+"%");
		if(estoqueFilter.getEstoque()!= null)
			query.setParameter("estoque", estoqueFilter.getEstoque());
		if(estoqueFilter.getEstoqueMinimo()!= null)
			query.setParameter("estoque_minimo", estoqueFilter.getEstoqueMinimo());
		if(estoqueFilter.getGondola()!= null)
			query.setParameter("gondola", estoqueFilter.getGondola());
		
		
		List<Estoque> dados =  query.getResultList();
		
		return dados; 
	}

	private Predicate[] criarRestricoes(EstoqueFilter estoqueFilter, CriteriaBuilder builder, Root<Estoque> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		if(!StringUtils.isEmpty(estoqueFilter.getProduto().getNome()))
			predicates.add(builder.like(
					builder.lower(root.get("produto.nome")), 
					"%"+ estoqueFilter.getProduto().getNome().toLowerCase()+ "%"));
		if( estoqueFilter.getEstoque() > 0)
			predicates.add(builder.equal(root.get("estoque"), estoqueFilter.getEstoque()));
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
}
