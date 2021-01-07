package br.com.money.repository.filter;

import br.com.money.model.SimNao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
public class PessoaFilter {
	
	private String nome;

	private SimNao ativo;

}
