package br.com.goldhirsch.exception;

public class EmailNotFoundException extends RuntimeException {
	
	public EmailNotFoundException(String email) {
		super("Usuario não encontrado para este email "  + email);
	}

}
