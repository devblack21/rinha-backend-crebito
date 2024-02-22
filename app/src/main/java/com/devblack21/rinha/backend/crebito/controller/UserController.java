package com.devblack21.rinha.backend.crebito.controller;

import com.devblack21.rinha.backend.crebito.controller.dto.TransactionResponse;
import com.devblack21.rinha.backend.crebito.core.repository.TransactionRepository;
import com.devblack21.rinha.backend.crebito.core.repository.UserAccountRepository;
import com.devblack21.rinha.backend.crebito.repository.entity.UserAccountEntity;
import io.github.devblack21.logging.LogBit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Profile("bdd")
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class UserController {

    private static final String LOG_CODE = "USER_TECH_ENDPOINT";

    private final UserAccountRepository userAccountRepository;

    @PostMapping("/user")
    public ResponseEntity<TransactionResponse> post(final @RequestParam("id") byte id,
                                                    final @RequestParam("saldo") int saldo,
                                                    final @RequestParam("limite") int limite) {

        final UserAccountEntity user = userAccountRepository.save(UserAccountEntity.builder()
                        ._id(id)
                        .saldo(saldo)
                        .limite(limite)
                .build());

        LogBit.info(LOG_CODE, "Usuario salvo com sucesso.", user);

        return ResponseEntity.ok(new TransactionResponse(user.getSaldo(), user.getLimite()));
    }

    @DeleteMapping("/user/delete-all")
    public ResponseEntity<?> deleteAll() {

        userAccountRepository.deleteAll();

        LogBit.info(LOG_CODE, "Collections de usuarios deletada com sucesso");

        return ResponseEntity.ok("");
    }


}
