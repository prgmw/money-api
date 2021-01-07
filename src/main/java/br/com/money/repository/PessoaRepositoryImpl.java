package br.com.money.repository;

import br.com.money.model.Lancamento;
import br.com.money.model.Pessoa;
import br.com.money.model.projection.ResumoLancamento;
import br.com.money.repository.filter.LancamentoFilter;
import br.com.money.repository.filter.PessoaFilter;
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

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Pessoa> filtrar(PessoaFilter pessoa, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
        Root<Pessoa> root = criteria.from(Pessoa.class);

        Predicate[] predicates = criarRestricoes(pessoa, builder, root);
        criteria.where(predicates);
        manager.createQuery(criteria);

        TypedQuery<Pessoa> query = manager.createQuery(criteria);

        addRestricoesPaginacao(query, pageable);
        
        return new PageImpl<>(query.getResultList(), pageable, total(pessoa));
    }

    private Long total(PessoaFilter pessoa) {
    	CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Pessoa> root = criteria.from(Pessoa.class);

        Predicate[] predicates = criarRestricoes(pessoa, builder, root);

        criteria.where(predicates);
        criteria.select(builder.count(root));

		return manager.createQuery(criteria).getSingleResult();
	}

	private void addRestricoesPaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int registrosPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * registrosPagina;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(registrosPagina);
	}

	private Predicate[] criarRestricoes(PessoaFilter pessoa, CriteriaBuilder builder, Root<Pessoa> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(pessoa.getNome())) {
            predicates.add(builder.like(builder.lower(root.get("nome")), "%" + pessoa.getNome() + "%"));
        }

        if (!StringUtils.isEmpty(pessoa.getAtivo())) {
            predicates.add(builder.like(builder.lower(root.get("ativo")), pessoa.getAtivo().name()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
