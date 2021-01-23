package br.com.goldhirsch.security.jwt;

import br.com.goldhirsch.exception.EmailNotFoundException;
import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;


    public String gerarToken(Authentication authentication){

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts
                .builder()
                .setIssuer("API Goldhirsch")
                .setSubject(usuarioLogado.getEmail())
                .setExpiration(data)
                .signWith( SignatureAlgorithm.HS512, chaveAssinatura )
                .claim("nome", usuarioLogado.getNome())
                .claim("sobrenome", usuarioLogado.getSobrenome())
                .claim("idUsuario", usuarioLogado.getId())
                .compact();
    }

    public Claims obterClaims (String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token){
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);

        }catch (Exception e){
            return false;
        }
    }

    public String obterEmail(String token){
        return (String) obterClaims(token).getSubject();
    }
}
