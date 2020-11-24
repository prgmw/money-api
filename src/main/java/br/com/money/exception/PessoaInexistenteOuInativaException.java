package br.com.money.exception;

public class PessoaInexistenteOuInativaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PessoaInexistenteOuInativaException(String mensagem) {
		super(mensagem);
	}

}
