package com.devblack21.rinha.backend.crebito.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionResponse(@JsonProperty("saldo") int saldo,
                                  @JsonProperty("limite") int limite) {



}
