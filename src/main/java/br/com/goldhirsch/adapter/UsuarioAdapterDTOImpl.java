package br.com.goldhirsch.adapter;

import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.request.LoginFormRequest;
import br.com.goldhirsch.response.AuthenticationResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAdapterDTOImpl implements AdapterDTO<Usuario, AuthenticationResponse, LoginFormRequest> {

    @Override
    public Usuario ToEntity(LoginFormRequest loginFormRequest) {
        return null;
    }
    @Override
    public AuthenticationResponse toResponse(Usuario model) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setId(model.getId());
        response.setNome(model.getNome());
        response.setSobrenome(model.getSobrenome());
        response.setEmail(model.getEmail());
        return response;
    }

}
