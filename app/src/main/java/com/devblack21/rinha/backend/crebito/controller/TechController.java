package com.devblack21.rinha.backend.crebito.controller;

import com.devblack21.rinha.backend.crebito.core.processor.TechProcessor;
import com.devblack21.rinha.backend.crebito.domain.Cliente;
import io.github.devblack21.logging.LogBit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Profile("bdd")
@RequestMapping("/tech")
@RequiredArgsConstructor
public class TechController {

    private static final String LOG_CODE = "USER_TECH_ENDPOINT";

    private final TechProcessor techProcessor;

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> post(final @RequestParam("id") byte id,
                                                    final @RequestParam("saldo") int saldo,
                                                    final @RequestParam("limite") int limite) {

        final Cliente user = techProcessor.saveUser(new Cliente(id, saldo, limite));

        LogBit.info(LOG_CODE, "Usuario salvo com sucesso.", user);

        return ResponseEntity.ok(
                Map.of(
                "id", user.getId(),
                "saldo", user.getSaldo(),
                "limite", user.getLimite()
                )
        );
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAll() {
        techProcessor.deleteAll();
        return ResponseEntity.ok("");
    }

}
