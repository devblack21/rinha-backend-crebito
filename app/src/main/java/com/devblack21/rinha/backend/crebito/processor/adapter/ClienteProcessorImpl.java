package com.devblack21.rinha.backend.crebito.processor.adapter;

import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.core.exception.NotFoundException;
import com.devblack21.rinha.backend.crebito.core.exception.NotLimitException;
import com.devblack21.rinha.backend.crebito.core.processor.ClienteProcessor;
import com.devblack21.rinha.backend.crebito.core.repository.TransacaoRepository;
import com.devblack21.rinha.backend.crebito.domain.EnvioTransacao;
import com.devblack21.rinha.backend.crebito.domain.Extrato;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClienteProcessorImpl implements ClienteProcessor {

    private final TransacaoRepository transacaoRepository;
    private final JsonMapper jsonMapper;

    @Override
    @Transactional
    public EnvioTransacao saveTransaction(final TransactionRequest transactionRequest, byte id) throws IOException {
        try {
            final String jsonRawValue = transacaoRepository.envioTransacao(id,
                    transactionRequest.descricao(),
                    Character.toLowerCase(transactionRequest.tipo()),
                    transactionRequest.valor());

            if (Objects.isNull(jsonRawValue) || jsonRawValue.isEmpty()) {
                throw new NotFoundException();
            }

            return jsonMapper.readValue(jsonRawValue, EnvioTransacao.class);
        } catch (final JpaSystemException exception) {
            throw new NotLimitException("O cliente não tem limite para executar essa transação.");
        }
    }


    @Override
    @Transactional
    public Extrato getExtract(byte id) throws IOException {
        try {
            return jsonMapper.readValue(transacaoRepository.getTransacoes(id), Extrato.class);
        } catch (final JpaSystemException exception) {
            throw new NotFoundException();
        }
    }

}
