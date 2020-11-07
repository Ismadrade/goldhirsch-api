package br.com.goldhirsch.service;

import br.com.goldhirsch.exception.BadCredentialsException;
import br.com.goldhirsch.exception.EmailNotFoundException;
import br.com.goldhirsch.exception.UsuarioException;
import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getEmail());
        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());
        if(senhasBatem){
            return user;
        }
        throw new BadCredentialsException(usuario.getEmail());
    }

    @Transactional
    public Usuario getUsuario(String email) {
        Usuario usuario = repository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
        return usuario;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("Email não encontrado na base de dados!"));

        return User
                .builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles("USER")
                .build();
    }
}
