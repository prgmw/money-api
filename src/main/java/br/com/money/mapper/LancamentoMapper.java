package br.com.money.mapper;

import org.mapstruct.Mapper;

import br.com.money.http.LancamentoDTO;
import br.com.money.model.Lancamento;

@Mapper(componentModel="spring")
public interface LancamentoMapper {

	Lancamento lancamentoDTOToLancamento(LancamentoDTO lancamentoDto);
	LancamentoDTO lancamentoToLancamentoDTO(Lancamento lancamento);

}
