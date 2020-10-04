package br.com.goldhirsch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.goldhirsch.exception.BadCredentialsException;
import br.com.goldhirsch.exception.EmailNotFoundException;
import br.com.goldhirsch.exception.UsuarioException;
import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario salvarUsuario(Usuario usuario) {
		boolean existeUsuario = repository.existsByEmail(usuario.getEmail());
		if(existeUsuario) {
			throw new UsuarioException(usuario.getEmail());
		}
		return repository.save(usuario);
		
		
	}
	
	public Usuario getUsuario(String email) {
		Usuario usuario = repository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
		if(!email.equals(usuario.getEmail())) {
			throw new BadCredentialsException(email); 
		}
		
		return usuario;
		
		
	}

}
