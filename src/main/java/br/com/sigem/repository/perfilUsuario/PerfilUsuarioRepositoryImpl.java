package br.com.sigem.repository.perfilUsuario;

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

import br.com.sigem.model.PerfilUsuario;
import br.com.sigem.repository.filter.PerfilUsuarioFilter;

public class PerfilUsuarioRepositoryImpl implements PerfilUsuarioRepositoryQuery{

	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<PerfilUsuario> filtrar(PerfilUsuarioFilter perfilUsuarioFilter) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<PerfilUsuario> criteria = builder.createQuery(PerfilUsuario.class);
		
		Root<PerfilUsuario> root = criteria.from(PerfilUsuario.class);
		
		Predicate[] predicates = criarRestricoes(perfilUsuarioFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<PerfilUsuario> query = manager.createQuery(criteria);
		
		//adicionarRestricoesDePaginacao(query, pageable);
		
		return query.getResultList(); 
	}


	


	private Predicate[] criarRestricoes(PerfilUsuarioFilter perfilUsuarioFilter, CriteriaBuilder builder, Root<PerfilUsuario> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		
		if(!StringUtils.isEmpty(perfilUsuarioFilter.getNome()))
			predicates.add(builder.like(
					builder.lower(root.get("nome")), 
					"%"+ perfilUsuarioFilter.getNome().toLowerCase()+ "%"));
		if(!StringUtils.isEmpty(perfilUsuarioFilter.getPermissao()))
			predicates.add(builder.like(
					builder.lower(root.get("permissao")), 
					"%"+ perfilUsuarioFilter.getPermissao().toLowerCase()+ "%"));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}


	
	
}
