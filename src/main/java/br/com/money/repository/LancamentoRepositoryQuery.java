package br.com.money.repository;

import java.util.List;

import br.com.money.model.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.money.model.Lancamento;
import br.com.money.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	 Page<Lancamento> filtrar(LancamentoFilter lancamento, Pageable pageable);
	 Page<ResumoLancamento> resumoLancamento(LancamentoFilter lancamento, Pageable pageable);

}
