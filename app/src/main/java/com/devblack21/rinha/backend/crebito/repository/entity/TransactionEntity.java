package com.devblack21.rinha.backend.crebito.repository.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Document(collection = "transactions")
public class TransactionEntity {

    private String _id = UUID.randomUUID().toString();
    private byte userAccountId;
    private char tipo;
    private int valor;
    private String descricao;
    private LocalDateTime dateTime;

}
