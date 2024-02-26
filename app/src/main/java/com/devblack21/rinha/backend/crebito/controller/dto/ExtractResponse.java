package com.devblack21.rinha.backend.crebito.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExtractResponse(@JsonProperty("saldo") SaldoDTO saldo,
                              @JsonProperty("ultimas_transacoes") List<TransactionDTO> transactions) {

    public record SaldoDTO(@JsonProperty("total") int total,
            @JsonProperty("data_extrato")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            @JsonSerialize(using = LocalDateTimeSerializer.class)
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
            LocalDateTime dataTime,
            @JsonProperty("limite") int limite) {

    }

    public record TransactionDTO(@JsonProperty("valor") int valor,
                                 @JsonProperty("tipo") char tipo,
                                 @JsonProperty("descricao") String descricao,
                                 @JsonProperty("realizada_em")
                                 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                 @JsonSerialize(using = LocalDateTimeSerializer.class)
                                 @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                                 LocalDateTime dataTime) {

    }
}

