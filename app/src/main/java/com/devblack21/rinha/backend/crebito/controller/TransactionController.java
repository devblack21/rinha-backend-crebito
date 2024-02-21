package com.devblack21.rinha.backend.crebito.controller;

import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.core.repository.TransactionRepository;
import com.devblack21.rinha.backend.crebito.core.repository.UserAccountRepository;
import com.devblack21.rinha.backend.crebito.repository.entity.TransactionEntity;
import com.devblack21.rinha.backend.crebito.repository.entity.UserAccountEntity;
import io.github.devblack21.logging.LogBit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepository;

    private final UserAccountRepository userAccountRepository;

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<?> post(final @PathVariable("id") int id,
                                 final @RequestBody TransactionRequest transactionRequest) {

        UserAccountEntity user = userAccountRepository.findBy_id((byte) id);

        if (user.getLimite() == 80000) {
            LogBit.error("TRANSACTION", "Erro ao cadastrar exceção");
            return ResponseEntity.badRequest().body(user);
        }

        LogBit.info("TRANSACTION", "Cadastrando Transação", transactionRequest);

        var returno = transactionRepository.save(TransactionEntity.builder()
                        .dateTime(LocalDateTime.now())
                        .userAccountId((byte) id)
                        .valor(transactionRequest.valor())
                        .descricao(transactionRequest.descricao())
                        .tipo(transactionRequest.tipo().charAt(0))
                .build());

        return ResponseEntity.ok(returno);
    }



}
