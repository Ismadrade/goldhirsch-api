package br.com.goldhirsch.response;

import br.com.goldhirsch.model.Usuario;

public class AuthenticationResponse {

    private Usuario usuario;
    private String token;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
