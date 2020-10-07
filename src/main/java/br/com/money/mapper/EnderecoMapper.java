package br.com.money.mapper;

import org.mapstruct.Mapper;

import br.com.money.http.EnderecoDTO;
import br.com.money.model.Endereco;

@Mapper(componentModel="spring")
public interface EnderecoMapper {
	
	Endereco enderecoDTOToEndereco(EnderecoDTO enderecoDto);
	EnderecoDTO enderecoToEnderecoDTO(Endereco endereco);
	
}
