package br.com.goldhirsch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.goldhirsch.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	boolean existsByEmail(String email);
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findById(Integer id);

}
