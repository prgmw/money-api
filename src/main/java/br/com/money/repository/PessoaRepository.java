package br.com.money.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.money.model.Pessoa;

public interface PessoaRepository extends PessoaRepositoryQuery, JpaRepository<Pessoa, Long> {
	
}
