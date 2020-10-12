package br.com.goldhirsch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import br.com.goldhirsch.model.Lancamento;
import br.com.goldhirsch.repository.LancamentoRepository;

import java.util.List;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;
	
	@Transactional
	public Lancamento inserirLancamento(Lancamento lancamento) {		
		return  repository.save(lancamento);
	}

	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example example = Example.of(lancamentoFiltro, ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING) );
		return repository.findAll(example);
	}
}
