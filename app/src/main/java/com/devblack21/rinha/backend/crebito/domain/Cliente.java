package com.devblack21.rinha.backend.crebito.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Cliente {

    private byte id;
    private int saldo;
    private int limite;

}
