package br.com.sigem.repository.produto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.sigem.model.Produto;
import br.com.sigem.model.relatorio.EntradaProduto;
import br.com.sigem.repository.filter.ProdutoFilter;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Produto> filtrar(ProdutoFilter produtoFilter) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		
		Root<Produto> root = criteria.from(Produto.class);
		
		Predicate[] predicates = criarRestricoes(produtoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Produto> query = manager.createQuery(criteria);
		
		//adicionarRestricoesDePaginacao(query, pageable);
		
		return query.getResultList(); 
	}

	@Override
	public List<Produto> filtrarValidade(ProdutoFilter produtoFilter) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		
		Root<Produto> root = criteria.from(Produto.class);
		
		Calendar rightNow = Calendar.getInstance();
		String month = String.valueOf(rightNow.get(Calendar.MONTH) + 1);
		LocalDate dataInicio;
		LocalDate dataFim;
		if (month.equals("10") || month.equals("11") || month.equals("12")) {
			String date = "2018-" + month + "-01";
			dataInicio = LocalDate.parse(date);
			date = "2018-" + month + "-30";
			dataFim = LocalDate.parse(date);
		}else {
			String date = "2018-0" + month + "-01";
			dataInicio = LocalDate.parse(date);
			date = "2018-0" + month + "-30";
			dataFim = LocalDate.parse(date);
		}
		Predicate[] predicates = criarRestricoesValidade(dataInicio, dataFim, produtoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Produto> query = manager.createQuery(criteria);
				
		return query.getResultList(); 
	}
	


	private Predicate[] criarRestricoes(ProdutoFilter produtoFilter, CriteriaBuilder builder, Root<Produto> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		
		if(!StringUtils.isEmpty(produtoFilter.getNome()))
			predicates.add(builder.like(
					builder.lower(root.get("nome")), 
					"%"+ produtoFilter.getNome().toLowerCase()+ "%"));
		if(!StringUtils.isEmpty(produtoFilter.getLote()))
			predicates.add(builder.like(
					builder.lower(root.get("lote")), 
					"%"+ produtoFilter.getLote().toLowerCase()+ "%"));
		if(!StringUtils.isEmpty(produtoFilter.getMarca()))
			predicates.add(builder.like(
					builder.lower(root.get("marca")), 
					"%"+ produtoFilter.getMarca().toLowerCase()+ "%"));
		if(!StringUtils.isEmpty(produtoFilter.getDescricao()))
			predicates.add(builder.like(
					builder.lower(root.get("descricao")), 
					"%"+ produtoFilter.getDescricao().toLowerCase()+ "%"));
		if(produtoFilter.getQuantidade() != null && produtoFilter.getQuantidade() > 0)
			predicates.add(builder.equal(root.get("quantidade"), produtoFilter.getQuantidade()));
		if(produtoFilter.getQuantidadeMinima() != null && produtoFilter.getQuantidadeMinima() > 0)
			predicates.add(builder.equal(root.get("quantidadeMinima"), produtoFilter.getQuantidadeMinima()));
		if(produtoFilter.getCodigo() != null && produtoFilter.getCodigo() > 0)
			predicates.add(builder.equal(root.get("codigo"), produtoFilter.getCodigo()));
		if(produtoFilter.getPrecoUnitario() != null && produtoFilter.getPrecoUnitario() > 0)
			predicates.add(builder.equal(root.get("precoUnitario"), produtoFilter.getPrecoUnitario()));
		if(produtoFilter.getPrecoLote() != null && produtoFilter.getPrecoLote() > 0)
			predicates.add(builder.equal(root.get("precoLote"), produtoFilter.getPrecoLote()));
		if(produtoFilter.getPericivel() != null && produtoFilter.getPericivel())
			predicates.add(builder.equal(root.get("perecivel"), produtoFilter.getPericivel()));
		if(produtoFilter.getDataCompra() != null && produtoFilter.getDataCompra() != null)
			predicates.add(builder.equal(root.get("dataCompra"), produtoFilter.getDataCompra()));
		if(produtoFilter.getValidade() != null && produtoFilter.getValidade() != null)
			predicates.add(builder.equal(root.get("validade"), produtoFilter.getValidade()));
		
		predicates.add(builder.equal(root.get("ativo"), 1));
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private Predicate[] criarRestricoesValidade(LocalDate dataInicio, LocalDate dataFim, ProdutoFilter produtoFilter, CriteriaBuilder builder, Root<Produto> root) {
		List<Predicate> predicates	= new ArrayList<>();

		if(dataInicio != null && dataFim != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("validade"), dataInicio));
			predicates.add(builder.lessThanOrEqualTo(root.get("validade"), dataFim));
		}
		
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
}
