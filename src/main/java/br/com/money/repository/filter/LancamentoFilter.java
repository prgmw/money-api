package br.com.money.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LancamentoFilter {
	
	private String descricao;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataVencimentoInicio;	
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataVencimentoFim;

}
