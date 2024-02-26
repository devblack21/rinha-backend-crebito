package com.devblack21.rinha.backend.crebito.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioTransacao {

   @JsonIgnore
   byte id;
   int saldo;
   int limite;

}
