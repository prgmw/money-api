package br.com.money.http;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.money.model.SimNao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaDTO {
	
	@JsonProperty("codigo")
	private Long codigo;
	
	@Size(min = 10, max = 100)
	@NotNull(message = "Nome não pode ser nulo")
	@JsonProperty("nome")
	private String nome;
	
	@JsonProperty("endereco")
	private EnderecoDTO endereco;
	
	@NotNull(message = "Ativo não pode ser nulo")
	@JsonProperty("ativo")
	private SimNao ativo;

}
