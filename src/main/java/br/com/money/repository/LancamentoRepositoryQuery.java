package br.com.money.repository;

import br.com.money.model.Lancamento;
import br.com.money.model.projection.ResumoLancamento;
import br.com.money.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    Page<Lancamento> filtrar(LancamentoFilter lancamento, Pageable pageable);

    Page<ResumoLancamento> resumoLancamento(LancamentoFilter lancamento, Pageable pageable);

}
