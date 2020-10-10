package br.com.goldhirsch.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.goldhirsch.model.Lancamento;
import br.com.goldhirsch.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	private LancamentoRepository repository;
	
	@Transactional
	public Lancamento inserirLancamento(Lancamento lancamento) {
		return  repository.save(lancamento);
	}
}
