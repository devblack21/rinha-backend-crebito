package com.devblack21.rinha.backend.crebito.bdd.core;

import com.devblack21.rinha.backend.crebito.controller.dto.ExtractResponse;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionResponse;
import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Component
@ScenarioScope
@NoArgsConstructor
public class TestContext {

    private UUID uuid;
    private int userId;
    private int saldo;
    private int limite;
    private String descricao;
    private String tipo;
    private Integer valor;
    private int saldoEsperado;
    private Map<String, Object> transactionRequest;
    private ResponseEntity<ExtractResponse> extractResponse;
    private ResponseEntity<TransactionResponse> transactionResponse;


}
