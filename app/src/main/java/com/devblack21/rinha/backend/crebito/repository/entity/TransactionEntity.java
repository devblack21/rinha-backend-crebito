package com.devblack21.rinha.backend.crebito.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transacoes")
public class TransactionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "cliente_id")
    private byte clienteId;
    @Column(name = "tipo")
    private char tipo;
    @Column(name = "valor")
    private int valor;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "data_hora",
            nullable = false,
            columnDefinition = "TIMESTAMP")
    private LocalDateTime dataHora;

}
