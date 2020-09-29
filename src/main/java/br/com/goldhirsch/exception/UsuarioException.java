package br.com.goldhirsch.exception;

public class UsuarioException extends RuntimeException {
	
	public UsuarioException(String email) {
		super("Usuario já cadastrado para o email "  + email);
	}

}
