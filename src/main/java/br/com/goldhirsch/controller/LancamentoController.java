package br.com.goldhirsch.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
