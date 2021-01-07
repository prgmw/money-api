package br.com.money.service;

import java.util.List;

import br.com.money.model.Lancamento;

public interface ILancamentoService {

    Lancamento atualizar(Long codigo, Lancamento lancamento);

    Lancamento criar(Lancamento pessoa);

	List<Lancamento> obterTodos();

	Lancamento obterPorId(Long id);
}
