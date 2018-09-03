package br.com.sigem.repository.usuario;

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

import br.com.sigem.model.Usuario;
import br.com.sigem.repository.filter.UsuarioFilter;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery{

	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Usuario> filtrar(UsuarioFilter usuarioFilter) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		
		Root<Usuario> root = criteria.from(Usuario.class);
		
		Predicate[] predicates = criarRestricoes(usuarioFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Usuario> query = manager.createQuery(criteria);
		
		//adicionarRestricoesDePaginacao(query, pageable);
		
		return query.getResultList(); 
	}


	


	private Predicate[] criarRestricoes(UsuarioFilter usuarioFilter, CriteriaBuilder builder, Root<Usuario> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		
		if(!StringUtils.isEmpty(usuarioFilter.getNome()))
			predicates.add(builder.like(
					builder.lower(root.get("nome")), 
					"%"+ usuarioFilter.getNome().toLowerCase()+ "%"));
		if(!StringUtils.isEmpty(usuarioFilter.getEmail()))
			predicates.add(builder.like(
					builder.lower(root.get("email")), 
					"%"+ usuarioFilter.getEmail().toLowerCase()+ "%"));
		if(!StringUtils.isEmpty(usuarioFilter.getNomeUsuario()))
			predicates.add(builder.like(
					builder.lower(root.get("nomeUsuario")), 
					"%"+ usuarioFilter.getNomeUsuario().toLowerCase()+ "%"));
		if(!StringUtils.isEmpty(usuarioFilter.getCpf()))
			predicates.add(builder.like(
					builder.lower(root.get("cpf")), 
					"%"+ usuarioFilter.getCpf().toLowerCase()+ "%"));
		if(!StringUtils.isEmpty(usuarioFilter.getCargo()))
			predicates.add(builder.like(
					builder.lower(root.get("cargo")), 
					"%"+ usuarioFilter.getCargo().toLowerCase()+ "%"));
		if(usuarioFilter.getDataNascimento() != null)
			predicates.add(builder.equal(root.get("dataNascimento"), usuarioFilter.getDataNascimento()));

		
		predicates.add(builder.equal(root.get("ativo"), usuarioFilter.getAtivo()?1:0));		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}


	private void adicionarRestricoesDePaginacao(TypedQuery<Usuario> query, Pageable pageable) {
			
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
		
	}

	private Long total(UsuarioFilter usuarioFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		
		Predicate[] predicates = criarRestricoes(usuarioFilter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}
	
	
}
