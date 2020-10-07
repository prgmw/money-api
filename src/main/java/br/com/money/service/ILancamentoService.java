package br.com.money.service;

import java.util.List;

import br.com.money.model.Lancamento;

public interface ILancamentoService {
	
	Lancamento criar(Lancamento pessoa);

	List<Lancamento> obterTodos();

	Lancamento obterPorId(Long id);
}
