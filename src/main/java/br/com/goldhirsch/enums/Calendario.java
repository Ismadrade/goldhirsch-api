package br.com.goldhirsch.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Calendario {
    JANEIRO(0, "Janeiro"),
    FEVEREIRO(1, "Fevereiro"),
    MARCO(2, "Março"),
    ABRIL(3, "Abril"),
    MAIO(4, "Maio"),
    JUNHO(5, "Junho"),
    JULHO(6, "Julho"),
    AGOSTO(7, "Agosto"),
    SETEMBRO(8, "Setembro"),
    OUTUBRO(9, "Outubro"),
    NOVEMBRO(10, "Novembro"),
    DEZEMBRO(11, "Dezembro");

    private final Integer codigo;
    private final String mes;

    Calendario(Integer codigo, String mes) {
        this.codigo = codigo;
        this.mes = mes;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getMes() {
        return mes;
    }

    @JsonCreator
    public static Calendario forValues(@JsonProperty("codigo") Integer codigo, @JsonProperty("mes") String mes){
        for (Calendario calendario : Calendario.values()) {
            if ( calendario.codigo.equals(codigo) && calendario.mes.equals(mes)) {
                return calendario;
            }
        }
        return null;
    }

}
