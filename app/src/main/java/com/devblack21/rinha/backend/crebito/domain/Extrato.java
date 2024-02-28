package com.devblack21.rinha.backend.crebito.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"saldo", "ultimas_transacoes"})
public class Extrato {

    @JsonProperty("saldo")
    Saldo saldo;

    @JsonProperty("ultimas_transacoes")
    List<Transaction> transacoes;

    @JsonCreator
    public Extrato(@JsonProperty("transacoes") final List<Transaction> transacoes,
                   @JsonProperty("saldo") Saldo saldo) {
        this.saldo = saldo;
        this.transacoes = transacoes;
    }

    @Data
    public static class Saldo {

        @JsonIgnore byte id;

        @JsonProperty("total") int total;

        @JsonProperty("data_extrato")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime dataTime = LocalDateTime.now();

        @JsonProperty("limite") int limite;

        @JsonCreator
        public Saldo(@JsonProperty("saldo") final int saldo) {
            this.total = saldo;
        }

    }

    @Data
    public static class Transaction {

        @Setter @JsonProperty("valor") int valor;

        @Setter @JsonProperty("tipo")  char tipo;

        @Setter @JsonProperty("descricao") String descricao;

        @JsonProperty("realizado_em")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        LocalDateTime dataHora;

        @JsonCreator
        public Transaction(@JsonProperty("data_hora") final String dataHora) {
            if (Objects.nonNull(dataHora) && !dataHora.isEmpty()) {
                final String data = dataHora.substring(0, 19);
                //TODO: lógica de arredondamento do valor da data
                final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                this.dataHora = LocalDateTime.parse(data, dtf);
            }
        }
    }

}
