package br.com.money.service;

import java.util.List;

import br.com.money.model.Pessoa;
import br.com.money.model.SimNao;

public interface IPessoaService {

	Pessoa atualizar(Pessoa pessoa, Long codigoPessoa);
	
	Pessoa criar(Pessoa pessoa);

	void remover(Long id);

	List<Pessoa> obterTodos();

	Pessoa obterPorId(Long id);

	void atualizarStatus(SimNao ativo, Long codigo);
}
