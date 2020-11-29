package br.com.goldhirsch.adapter;

import br.com.goldhirsch.enums.TipoLancamento;
import br.com.goldhirsch.exception.LancamentoException;
import br.com.goldhirsch.model.Lancamento;
import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.request.LancamentoRequest;
import br.com.goldhirsch.response.LancamentoResponse;
import br.com.goldhirsch.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LancamentoAdapterDTOImpl implements AdapterDTO<Lancamento, LancamentoResponse, LancamentoRequest> {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Lancamento ToEntity(LancamentoRequest request) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao(request.getDescricao());
        lancamento.setMes(request.getMes());
        lancamento.setAno(request.getAno());
        lancamento.setValor(request.getValor());
        Usuario usuario = usuarioService
                .getUsuarioById(request.getUsuario())
                .orElseThrow(() -> new LancamentoException("Usuário não encontrado para o id informado"));;
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
        response.setMes(model.getMes());
        response.setAno(model.getAno());
        response.setUsuario(model.getUsuario());
        response.setValor(model.getValor());
        response.setTipo(model.getTipo());
        return response;
    }
}