package br.com.money.service;

import java.util.List;

import br.com.money.model.Pessoa;
import br.com.money.model.SimNao;
import br.com.money.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPessoaService {

	Pessoa atualizar(Pessoa pessoa, Long codigoPessoa);
	
	Pessoa criar(Pessoa pessoa);

	void remover(Long id);

	List<Pessoa> obterTodos();

	Pessoa obterPorId(Long id);

	void atualizarStatus(SimNao ativo, Long codigo);

	Page<Pessoa> pesquisar(PessoaFilter filter, Pageable pageable);
}
