package br.com.money.repository;

import br.com.money.model.Lancamento;
import br.com.money.repository.filter.LancamentoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamento, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamento, builder, root);
        criteria.where(predicates);
        manager.createQuery(criteria);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);

        addRestricoesPaginacao(query, pageable);
        
        return new PageImpl<>(query.getResultList(), pageable, total(lancamento));
    }

    private Long total(LancamentoFilter lancamento) {
    	CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);
        
        Predicate[] predicates = criarRestricoes(lancamento, builder, root);
        
        criteria.where(predicates);
        criteria.select(builder.count(root));
        
		return manager.createQuery(criteria).getSingleResult();
	}

	private void addRestricoesPaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int registrosPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * registrosPagina;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(registrosPagina);
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamento, CriteriaBuilder builder, Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(lancamento.getDescricao())) {
            predicates.add(builder.like(builder.lower(root.get("descricao")), "%" + lancamento.getDescricao() + "%"));
        }
        if (lancamento.getDataVencimentoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), lancamento.getDataVencimentoInicio()));
        }
        if (lancamento.getDataVencimentoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"), lancamento.getDataVencimentoFim()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
