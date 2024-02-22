package com.devblack21.rinha.backend.crebito.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExtractResponse(@JsonProperty("saldo") SaldoDTO saldo,
                              @JsonProperty("ultimas_transacoes") List<TransactionDTO> transactions) {

    public record SaldoDTO(@JsonProperty("total") int total,
            @JsonProperty("data_extrato") LocalDateTime dataTime,
            @JsonProperty("limite") int limite) {


    }

    public record TransactionDTO(@JsonProperty("valor") int valor,
                                 @JsonProperty("tipo") char tipo,
                                 @JsonProperty("descricao") String descricao,
                                 @JsonProperty("realizada_em") LocalDateTime dataTime) {

    }
}

