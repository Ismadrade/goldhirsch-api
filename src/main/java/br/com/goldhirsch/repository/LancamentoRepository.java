package br.com.goldhirsch.repository;

import br.com.goldhirsch.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.goldhirsch.model.Lancamento;

import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {
    List<Lancamento> findAllByUsuario(Usuario usuario);
}
