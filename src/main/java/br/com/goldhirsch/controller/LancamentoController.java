package br.com.goldhirsch.controller;

import java.util.List;
import java.util.Optional;

import br.com.goldhirsch.adapter.AdapterDTO;
import br.com.goldhirsch.request.LancamentoRequest;
import br.com.goldhirsch.response.LancamentoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.goldhirsch.exception.LancamentoException;
import br.com.goldhirsch.model.Lancamento;
import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.service.LancamentoService;
import br.com.goldhirsch.service.UsuarioService;

@Controller
@RequestMapping("lancamentos")
public class LancamentoController {
	
	@Autowired
	private LancamentoService service;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AdapterDTO<Lancamento, LancamentoResponse, LancamentoRequest> adapter;
	
	@PostMapping("/inserir-lancamento")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity inserirLancamento(@RequestBody LancamentoRequest dto) {
		try {
			Lancamento lancamento = adapter.ToEntity(dto);
			lancamento = service.inserirLancamento(lancamento);
			return ResponseEntity.ok(adapter.toResponse(lancamento));
		} catch (LancamentoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity buscarLancamento(@RequestParam("usuario") Integer idUsuario){
		Lancamento lancamentoFiltro = new Lancamento();
		Optional<Usuario> usuario = usuarioService.getUsuarioById(idUsuario);
		if(!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realiza a consulta. Usuario não encontrado para o id informado.");
		}else {
			lancamentoFiltro.setUsuario(usuario.get());
		}
		List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
		return ResponseEntity.ok(lancamentos);
	}

	@DeleteMapping("{id}")
	public ResponseEntity excluirLancamento(@PathVariable("id") Integer id){
		try{
			service.excluirLancamento(id);
			return ResponseEntity.ok().build();
		}catch (LancamentoException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity atualizar( @PathVariable("id") Integer id, @RequestBody Lancamento lancamento) {
		try {
			service.editarLancamento(id, lancamento);
			return ResponseEntity.ok(lancamento);
		}catch (LancamentoException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
