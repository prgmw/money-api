package br.com.money.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Long codigo;
	
	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "pessoa_codigo")
    private Pessoa pessoa;
	
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
