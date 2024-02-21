package com.devblack21.rinha.backend.crebito.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "user_account")
public class UserAccountEntity {

    @Id
    private byte _id;
    private int saldo;
    private int limite;

}
