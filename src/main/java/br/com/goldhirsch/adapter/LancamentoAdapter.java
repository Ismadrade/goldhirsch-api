package br.com.goldhirsch.adapter;

import br.com.goldhirsch.enums.Calendario;
import br.com.goldhirsch.enums.TipoLancamento;
import br.com.goldhirsch.exception.LancamentoException;
import br.com.goldhirsch.model.Lancamento;
import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.request.LancamentoRequest;
import br.com.goldhirsch.response.LancamentoResponse;
import br.com.goldhirsch.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LancamentoAdapter implements Adapter<Lancamento, LancamentoResponse, LancamentoRequest> {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    public Lancamento ToEntity(LancamentoRequest request) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao(request.getDescricao());
        lancamento.setMes(request.getCalendario().ordinal());
        lancamento.setAno(request.getAno());
        lancamento.setValor(request.getValor());
        Usuario usuario = usuarioService
                .getUsuarioById(request.getUsuario())
                .orElseThrow(() -> new LancamentoException("Usuário não encontrado para o id informado"));
        lancamento.setUsuario(usuario);
        lancamento.setTipo(TipoLancamento.valueOf(request.getTipo()));
        lancamento.setDataCadastro(LocalDate.now());
        return lancamento;
    }

    @Override
    public LancamentoResponse toResponse(Lancamento model) {
        LancamentoResponse response = new LancamentoResponse();
        response.setId(model.getId());
        response.setDescricao(model.getDescricao());
        response.setDataCadastro(model.getDataCadastro());
        response.setCalendario(Calendario.values()[model.getMes()]);
        response.setAno(model.getAno());
        response.setUsuario(model.getUsuario().getId());
        response.setValor(model.getValor());
        response.setTipo(model.getTipo());
        return response;
    }
}