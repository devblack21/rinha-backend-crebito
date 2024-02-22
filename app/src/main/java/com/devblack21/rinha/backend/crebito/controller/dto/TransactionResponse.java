package com.devblack21.rinha.backend.crebito.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionResponse(@JsonProperty("saldo") int saldo,
                                  @JsonProperty("limite") int limite) {



}
