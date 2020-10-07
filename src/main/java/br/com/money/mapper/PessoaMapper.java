package br.com.money.mapper;

import org.mapstruct.Mapper;

import br.com.money.http.PessoaDTO;
import br.com.money.model.Pessoa;

@Mapper(componentModel="spring")
public interface PessoaMapper {
	
	Pessoa pessoaDTOToPessoa(PessoaDTO pessoaDto);
	PessoaDTO pessoaToPessoaDTO(Pessoa pessoa);
	
}
