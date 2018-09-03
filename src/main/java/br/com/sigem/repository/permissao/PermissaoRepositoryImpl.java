package br.com.sigem.repository.permissao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.sigem.model.Permissao;

public class PermissaoRepositoryImpl implements PermissaoRepositoryQuery{

	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Permissao filtrar(String nome) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<Permissao> criteria = builder.createQuery(Permissao.class);
		
		Root<Permissao> root = criteria.from(Permissao.class);
		
		Predicate[] predicates = criarRestricoes(nome, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Permissao> query = manager.createQuery(criteria);
		
		//adicionarRestricoesDePaginacao(query, pageable);
		
		return query.getResultList().get(0); 
	}


	


	private Predicate[] criarRestricoes(String nome, CriteriaBuilder builder, Root<Permissao> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		
		if(!StringUtils.isEmpty(nome))
			predicates.add(builder.equal(
					builder.lower(root.get("nome")), 
					nome.toLowerCase()));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	
	
}
