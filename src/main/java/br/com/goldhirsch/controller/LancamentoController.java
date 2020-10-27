package br.com.goldhirsch.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.goldhirsch.dto.LancamentoDTO;
import br.com.goldhirsch.enums.TipoLancamento;
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
	
	@PostMapping("/inserir-lancamento")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity inserirLancamento(@RequestBody LancamentoDTO dto) {
		try {
			Lancamento lancamento = converterDTO(dto);
			lancamento = service.inserirLancamento(lancamento);
			return ResponseEntity.ok(lancamento);
			
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
	
	private Lancamento converterDTO(LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setMes(dto.getMes());
		lancamento.setAno(dto.getAno());
		lancamento.setValor(dto.getValor());		
		Usuario usuario = usuarioService
				.getUsuarioById(dto.getUsuario())
				.orElseThrow(() -> new LancamentoException("Usuário não encontrado para o id informado"));		
		lancamento.setUsuario(usuario);
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		lancamento.setDataCadastro(LocalDate.now());
		return lancamento;
	}

}
