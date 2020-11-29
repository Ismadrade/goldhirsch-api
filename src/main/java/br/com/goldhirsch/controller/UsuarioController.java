package br.com.goldhirsch.controller;

import br.com.goldhirsch.adapter.AdapterDTO;
import br.com.goldhirsch.request.LoginFormRequest;
import br.com.goldhirsch.exception.BadCredentialsException;
import br.com.goldhirsch.response.AuthenticationResponse;
import br.com.goldhirsch.security.jwt.JwtService;
import br.com.goldhirsch.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.goldhirsch.model.Usuario;


@Controller
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioServiceImpl service;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	private AdapterDTO<Usuario, AuthenticationResponse, LoginFormRequest> adapter;


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void salvarUsuario(@RequestBody Usuario usuario) {
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		service.salvarUsuario(usuario);
	}
	
	@PostMapping("/logar")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity consultaUsuario(@RequestBody LoginFormRequest form) {
		try {

			UsernamePasswordAuthenticationToken dadosLogin = form.converter();
			service.autenticar(form);
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = jwtService.gerarToken(authentication);
			AuthenticationResponse response = adapter.toResponse((Usuario)authentication.getPrincipal());
			response.setToken(token);
			return ResponseEntity.ok(response);
			
		}catch (UsernameNotFoundException | BadCredentialsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
