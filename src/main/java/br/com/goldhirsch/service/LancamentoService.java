package br.com.goldhirsch.service;

import br.com.goldhirsch.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public List<Lancamento> buscar() {
		Authentication usuarioAutenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) usuarioAutenticado.getPrincipal();
		return repository.findAllByUsuario(usuario);
	}

	@Transactional
	public void excluirLancamento( Integer id){
		repository
				.findById(id)
				.map( lancamento -> {
					repository.delete(lancamento);
					return Void.TYPE;
				});
	}

	@Transactional
	public void editarLancamento( Integer id, Lancamento lancamento){
		repository.findById(id).map( lanc -> {
			lancamento.setId(lanc.getId());
			return repository.save(lancamento);
		});
	}
}
