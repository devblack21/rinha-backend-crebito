package com.devblack21.rinha.backend.crebito.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record TransactionRequest(@JsonProperty(value = "valor", required = true) int valor,
                                 @JsonProperty(value = "tipo", required = true) char tipo,
                                 @JsonProperty(value = "descricao", required = true) String descricao) {



}
