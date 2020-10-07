package br.com.money.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroMessage {
	private String mensagemUsuario;	
	private String mensagemDesenvolvedor;
}
