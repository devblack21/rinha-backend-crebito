package com.devblack21.rinha.backend.crebito.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    private byte id;
    @Column(name = "saldo")
    private int saldo;
    @Column(name = "limite")
    private int limite;

}
