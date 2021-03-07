package br.com.goldhirsch.service;

import br.com.goldhirsch.request.LoginFormRequest;
import br.com.goldhirsch.exception.BadCredentialsException;
import br.com.goldhirsch.exception.EmailNotFoundException;
import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UsuarioRepository repository;

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    public void autenticar(LoginFormRequest form){
        UserDetails user = loadUserByUsername(form.getEmail());
        boolean senhasBatem = encoder.matches(form.getSenha(), user.getPassword());
        if(!senhasBatem){
            throw new BadCredentialsException(form.getEmail());
        }
    }

    @Transactional
    public Usuario getUsuario(String email) {
        Usuario usuario = repository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
        return usuario;

    }
    @Transactional
    public Optional<Usuario> getUsuarioById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("Email não encontrado na base de dados!"));

        if(usuario != null){
            return usuario;
        }
        throw new UsernameNotFoundException("Dados Inválidos");

    }
}
