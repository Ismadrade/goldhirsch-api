package br.com.goldhirsch.controller;

import br.com.goldhirsch.exception.BadCredentialsException;
import br.com.goldhirsch.security.jwt.JwtService;
import br.com.goldhirsch.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private UsuarioServiceImpl service;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void salvarUsuario(@RequestBody Usuario usuario) {
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		service.salvarUsuario(usuario);
	}
	
	@PostMapping("/logar")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity consultaUsuario(@RequestParam String email, String senha) {
		try {
			Usuario usuario = new Usuario();
			usuario.setEmail(email);
			usuario.setSenha(senha);
			UserDetails usuarioAutenticado = service.autenticar(usuario);
			String token = jwtService.gerarToken(usuario);
			System.out.println(token);
			return ResponseEntity.ok(usuarioAutenticado);
			
		}catch (UsernameNotFoundException | BadCredentialsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
