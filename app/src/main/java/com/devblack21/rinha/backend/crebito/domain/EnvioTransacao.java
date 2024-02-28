package com.devblack21.rinha.backend.crebito.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"limite", "saldo"})
public class EnvioTransacao {

   @JsonIgnore
   byte id;
   @JsonProperty("saldo") int saldo;
   @JsonProperty("limite") int limite;

}
