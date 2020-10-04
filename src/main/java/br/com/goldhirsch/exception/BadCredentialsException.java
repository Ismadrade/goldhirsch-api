package br.com.goldhirsch.exception;

public class BadCredentialsException extends RuntimeException {
	
	public BadCredentialsException(String email) {
		super("Login ou senha inválida para este email "  + email);
	}

}
