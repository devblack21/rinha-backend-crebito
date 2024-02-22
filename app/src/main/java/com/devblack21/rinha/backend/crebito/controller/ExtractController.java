package com.devblack21.rinha.backend.crebito.controller;

import com.devblack21.rinha.backend.crebito.controller.dto.ExtractResponse;
import com.devblack21.rinha.backend.crebito.core.repository.TransactionRepository;
import com.devblack21.rinha.backend.crebito.repository.entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ExtractController {

    private final TransactionRepository transactionRepository;
    @GetMapping("/{id}/extrato")
    public ResponseEntity<?> get(final @PathVariable("id") int id) {

        var transactions = transactionRepository.findFirst10ByUserAccountIdOrderByDateTimeDesc((byte) id);

        return ResponseEntity.ok(generate(transactions));
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

    private ExtractResponse generate(final Set<TransactionEntity> transactionEntities) {

        final ExtractResponse.SaldoDTO saldoDTO = new ExtractResponse.SaldoDTO(
                1000, LocalDateTime.now(), 10000);

        final List<ExtractResponse.TransactionDTO> list = new ArrayList<>();

        transactionEntities.forEach(transactionEntity -> {
            final ExtractResponse.TransactionDTO transactionDTO = new ExtractResponse.TransactionDTO(
                    transactionEntity.getValor(),
                    transactionEntity.getTipo(),
                    transactionEntity.getDescricao(),
                    transactionEntity.getDateTime()
            );
            list.add(transactionDTO);
        });



        return new ExtractResponse(saldoDTO, list);
    }

}
