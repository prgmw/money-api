package br.com.money.http;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnderecoDTO {
	
	@JsonProperty("codigo")
	private Long codigo;
	
	@Size(min = 10, max = 100)
	@NotNull(message = "logradouro não pode ser nulo")
	@JsonProperty("logradouro")
	private String logradouro;
	
	@NotNull(message = "numero não pode ser nulo")
	@JsonProperty("numero")
	private String numero;
	
	@JsonProperty("complemento")
	private String complemento;
	
	@NotNull(message = "bairro não pode ser nulo")
	@JsonProperty("bairro")
	private String bairro;
	
	@NotNull(message = "cep não pode ser nulo")
	@JsonProperty("cep")
	private String cep;
	
	@NotNull(message = "cidade não pode ser nulo")
	@JsonProperty("cidade")
	private String cidade;
	
	@Size(max = 2)
	@NotNull(message = "estado não pode ser nulo")
	@JsonProperty("estado")
	private String estado;
}
