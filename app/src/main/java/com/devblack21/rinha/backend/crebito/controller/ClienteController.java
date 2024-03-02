package com.devblack21.rinha.backend.crebito.controller;

import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.core.processor.ClienteProcessor;
import com.devblack21.rinha.backend.crebito.domain.EnvioTransacao;
import com.devblack21.rinha.backend.crebito.domain.Extrato;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteProcessor clienteProcessor;

    private final List<Character> acceptsTypes =  List.of('d', 'D', 'c', 'C');
    @PostMapping("/{id}/transacoes")
    public ResponseEntity<EnvioTransacao> post(final @PathVariable("id") byte id,
                                               final @RequestBody TransactionRequest transactionRequest) throws IOException {
        this.validate(transactionRequest);
        return ResponseEntity.ok(clienteProcessor.saveTransaction(transactionRequest, id));
    }


    @GetMapping("/{id}/extrato")
    public ResponseEntity<Extrato> get(final @PathVariable("id") byte id) throws IOException {
        return ResponseEntity.ok(clienteProcessor.getExtract(id));
    }


    private void validate(final TransactionRequest transactionRequest) {
        if (Objects.nonNull(transactionRequest)) {

            this.validateValor(transactionRequest);
            this.validateTipo(transactionRequest);
            this.validateDescricao(transactionRequest);

        } else {
            throw new IllegalArgumentException();
        }
    }

    private void validateValor(final TransactionRequest transactionRequest) {
        if (transactionRequest.valor() <= 0L) {
            throw new IllegalArgumentException();
        }
    }

    private void validateTipo(final TransactionRequest transactionRequest) {
        if ('\u0000' == transactionRequest.tipo() || !acceptsTypes.contains(transactionRequest.tipo())) {
            throw new IllegalArgumentException();
        }
    }
    private void validateDescricao(final TransactionRequest transactionRequest) {
        if (Objects.isNull(transactionRequest.descricao())
                || transactionRequest.descricao().length() > 10
                || transactionRequest.descricao().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }


}
