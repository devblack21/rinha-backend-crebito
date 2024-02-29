package com.devblack21.rinha.backend.crebito.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        @JsonProperty("total") long total;

        @JsonProperty("data_extrato")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime dataTime = LocalDateTime.now();

        @JsonProperty("limite") int limite;

        @JsonCreator
        public Saldo(@JsonProperty("saldo") final long saldo) {
            this.total = saldo;
        }

    }

    @Data
    public static class Transaction {

        @Setter @JsonProperty("valor") long valor;

        @Setter @JsonProperty("tipo")  char tipo;

        @Setter @JsonProperty("descricao") String descricao;

        @JsonProperty("realizado_em")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        LocalDateTime dataHora;

        @JsonCreator
        public Transaction(@JsonProperty("data_hora") final String dataHora) {
            if (Objects.nonNull(dataHora) && !dataHora.isEmpty()) {
                final String data = dataHora.substring(0, 19);

                final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                this.dataHora = LocalDateTime.parse(data, dtf);
            }
        }
    }

}
