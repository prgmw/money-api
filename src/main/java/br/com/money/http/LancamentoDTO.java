package br.com.money.http;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.money.model.Categoria;
import br.com.money.model.Pessoa;
import br.com.money.model.Tipo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LancamentoDTO {

	@Size(min = 10, max = 100)
	@NotNull(message = "Descricao não pode ser nulo")
	@JsonProperty("descricao")
	private String descricao;

	@NotNull
	@JsonProperty("dataVencimento")
	private LocalDate dataVencimento;

	@JsonProperty("dataPagamento")
	private LocalDate dataPagamento;

	@JsonProperty("valor")
	private BigDecimal valor;

	@JsonProperty("observacao")
	private String observacao;

	@NotNull(message = "Tipo não pode ser nulo")
	@JsonProperty("tipo")
	@Enumerated(EnumType.STRING)
	private Tipo tipo;

	@NotNull(message = "Categoria não pode ser nula")
	@JsonProperty("categoria")
	private Categoria categoria;

	@NotNull(message = "Pessoa não pode ser nula")
	@JsonProperty("pessoa")
	private Pessoa pessoa;

}
