package br.com.goldhirsch.request;

import br.com.goldhirsch.enums.Calendario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LancamentoRequest {

    private Integer id;
    private String descricao;
    private Calendario calendario;
    private Integer ano;
    private Integer usuario;
    private BigDecimal valor;
    private LocalDate dataCadastro;
    private String tipo;

}