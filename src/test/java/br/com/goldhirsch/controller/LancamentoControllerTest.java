package br.com.goldhirsch.controller;

import br.com.goldhirsch.enums.Calendario;
import br.com.goldhirsch.enums.TipoLancamento;
import br.com.goldhirsch.model.Lancamento;
import br.com.goldhirsch.model.Usuario;
import br.com.goldhirsch.request.LancamentoRequest;
import br.com.goldhirsch.response.LancamentoResponse;
import br.com.goldhirsch.service.LancamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class LancamentoControllerTest {

    static String LANCAMENTO_API="/lancamentos";

    @Autowired
    MockMvc mvc;

    @MockBean
    LancamentoService service;

    @Test
    @DisplayName("Deve criar um lançamento com sucesso.")
    public  void deveCriarUmLançamentoComSucesso() throws Exception {
        LancamentoRequest lancamentoRequest = newLancamentoRequest();
        LancamentoResponse response = newLancamentoResponse();
        Lancamento savedLancamento = newLancamento();
        BDDMockito.given(service.inserirLancamento(Mockito.any(Lancamento.class))).willReturn(savedLancamento);
        String json = new ObjectMapper().writeValueAsString(lancamentoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(LANCAMENTO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("descricao").value(response.getDescricao()))
                .andExpect(jsonPath("ano").value(response.getAno()))
                .andExpect(jsonPath("usuario").value(response.getUsuario()))
                .andExpect(jsonPath("valor").value(response.getValor()))
                .andExpect(jsonPath("tipo").value(response.getTipo().toString()));




    }

    private LancamentoRequest newLancamentoRequest(){
        return new LancamentoRequest()
                .builder()
                .descricao("Cartão de Crédito")
                .calendario(Calendario.JANEIRO)
                .ano(2021)
                .usuario(62)
                .valor(BigDecimal.valueOf(100.54))
                .tipo("RECEITA")
                .build();
    }

    private LancamentoResponse newLancamentoResponse(){
        return new LancamentoResponse()
                .builder()
                .id(1)
                .descricao("Cartão de Crédito")
                .calendario(Calendario.JANEIRO)
                .ano(2021)
                .usuario(62)
                .valor(BigDecimal.valueOf(100.54))
                .dataCadastro(LocalDate.of(2021, 9, 9))
                .tipo(TipoLancamento.RECEITA)
                .build();
    }

    private Usuario newUsuario(){
        return  Usuario
                    .builder()
                    .id(62)
                    .nome("Ismael")
                    .sobrenome("Andrade")
                    .email("maelandrade@vue.com.br")
                    .build();
    }

    private Lancamento newLancamento(){
        return Lancamento.builder()
                .id(1)
                .descricao("Cartão de Crédito")
                .mes(0)
                .ano(2021)
                .usuario(newUsuario())
                .valor(BigDecimal.valueOf(100.54))
                .dataCadastro(LocalDate.of(2021, 9, 9))
                .tipo(TipoLancamento.RECEITA)
                .build();
    }

}
