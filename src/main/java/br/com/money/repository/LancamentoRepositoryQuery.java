package br.com.money.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.money.model.Lancamento;
import br.com.money.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	 Page<Lancamento> filtrar(LancamentoFilter lancamento, Pageable pageable);
}
