package com.devblack21.rinha.backend.crebito.controller;

import com.devblack21.rinha.backend.crebito.controller.dto.ExtractResponse;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.core.processor.ClienteProcessor;
import com.devblack21.rinha.backend.crebito.domain.EnvioTransacao;
import io.github.devblack21.logging.LogBit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteProcessor clienteProcessor;

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<EnvioTransacao> post(final @PathVariable("id") byte id,
                                               final @RequestBody TransactionRequest transactionRequest) throws IOException {

        LogBit.setCorrelation(UUID.randomUUID().toString());
        LogBit.info("TRANSACTION", "Cadastrando Transação", transactionRequest);

        return ResponseEntity.ok(clienteProcessor.saveTransaction(transactionRequest, id));
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<ExtractResponse> get(final @PathVariable("id") byte id) {
        return ResponseEntity.ok(clienteProcessor.getExtract(id));
    }

}
