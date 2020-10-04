package br.com.goldhirsch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.goldhirsch.exception.UsuarioException;
import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.service.UsuarioService;

@Controller
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void salvarUsuario(@RequestBody Usuario usuario) {
		try {
			service.salvarUsuario(usuario);
			
		}catch (UsuarioException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PostMapping("/logar")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity consultaUsuario(@RequestParam String email) {
		try {
			Usuario usuario = service.getUsuario(email);
			return ResponseEntity.ok(usuario);
			
		}catch (UsuarioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
