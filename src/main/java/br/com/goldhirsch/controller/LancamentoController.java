package br.com.goldhirsch.controller;

import br.com.goldhirsch.adapter.Adapter;
import br.com.goldhirsch.exception.LancamentoException;
import br.com.goldhirsch.model.Lancamento;
import br.com.goldhirsch.request.LancamentoRequest;
import br.com.goldhirsch.response.LancamentoResponse;
import br.com.goldhirsch.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("lancamentos")
public class LancamentoController {
	
	@Autowired
	private LancamentoService service;
	

	@Autowired
	private Adapter<Lancamento, LancamentoResponse, LancamentoRequest> adapter;
	
	@PostMapping
	public ResponseEntity inserirLancamento(@RequestBody LancamentoRequest dto) {
		try {
			Lancamento lancamento = service.inserirLancamento(adapter.ToEntity(dto));
			return ResponseEntity.ok(adapter.toResponse(lancamento));
		} catch (LancamentoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity buscarLancamento(){

		List<Lancamento> lancamentos = service.buscar();
		List<LancamentoResponse> response = new ArrayList<>();
		lancamentos.stream()
				.forEach(entidade -> response.add(adapter.toResponse(entidade)));
		return ResponseEntity.ok(response);
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
	public ResponseEntity atualizar( @PathVariable("id") Integer id, @RequestBody LancamentoRequest lancamento) {
		try {
			service.editarLancamento(id, adapter.ToEntity(lancamento));
			return ResponseEntity.ok().build();
		}catch (LancamentoException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
