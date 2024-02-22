package com.devblack21.rinha.backend.crebito.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record TransactionRequest(@JsonProperty("valor") int valor,
                                 @JsonProperty("tipo") char tipo,
                                 @JsonProperty("descricao") String descricao) {



}
