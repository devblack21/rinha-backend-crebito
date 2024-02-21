package com.devblack21.rinha.backend.crebito.controller;

import com.devblack21.rinha.backend.crebito.core.repository.TransactionRepository;
import io.github.devblack21.logging.LogBit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ExtractController {

    private final TransactionRepository transactionRepository;
    @GetMapping("/{id}/extrato")
    public ResponseEntity<?> get(final @PathVariable("id") int id) {

        //controle de memoria -> retirar
        Runtime runtime = Runtime.getRuntime();
        LogBit.info("MAX_MEMORY", "Máximo de memória", humanReadableByteCountSI(runtime.maxMemory()));
        LogBit.info("FREE_MEMORY", "Memória livre", humanReadableByteCountSI(runtime.freeMemory()));

        return ResponseEntity.ok(transactionRepository.findFirst10ByUserAccountIdOrderByDateTimeDesc((byte) id));
    }


    public static String humanReadableByteCountSI(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }

}
