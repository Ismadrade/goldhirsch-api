package br.com.goldhirsch.response;

import br.com.goldhirsch.enums.Calendario;
import br.com.goldhirsch.enums.TipoLancamento;
import br.com.goldhirsch.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LancamentoResponse {
    private Integer id;
    private String descricao;
    private Calendario calendario;
    private Integer ano;
    private Integer usuario;
    private BigDecimal valor;
    private LocalDate dataCadastro;
    private TipoLancamento tipo;
}
