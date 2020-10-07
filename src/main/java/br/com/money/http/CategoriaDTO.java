package br.com.money.http;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaDTO {
	
	@JsonProperty("codigo")
	private Long codigo;
	
	@Size(min = 10, max = 100)
	@NotNull(message = "Nome não pode ser nulo")
	@JsonProperty("nome")
	private String nome;

}
