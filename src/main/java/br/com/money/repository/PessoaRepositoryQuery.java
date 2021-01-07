package br.com.money.repository;

import br.com.money.model.Pessoa;
import br.com.money.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryQuery {

    Page<Pessoa> filtrar(PessoaFilter lancamento, Pageable pageable);
}
