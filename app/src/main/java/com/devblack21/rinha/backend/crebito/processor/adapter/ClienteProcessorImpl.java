package com.devblack21.rinha.backend.crebito.processor.adapter;

import br.com.fluentvalidator.AbstractValidator;
import com.devblack21.rinha.backend.crebito.controller.dto.ExtractResponse;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.core.exception.NotFoundException;
import com.devblack21.rinha.backend.crebito.core.exception.NotLimitException;
import com.devblack21.rinha.backend.crebito.core.exception.ValidationRequestException;
import com.devblack21.rinha.backend.crebito.core.processor.ClienteProcessor;
import com.devblack21.rinha.backend.crebito.core.repository.ClienteRepository;
import com.devblack21.rinha.backend.crebito.core.repository.TransacaoRepository;
import com.devblack21.rinha.backend.crebito.domain.EnvioTransacao;
import com.devblack21.rinha.backend.crebito.repository.entity.ClienteEntity;
import com.devblack21.rinha.backend.crebito.repository.entity.TransactionEntity;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.devblack21.logging.LogBit;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClienteProcessorImpl implements ClienteProcessor {

    private final AbstractValidator<TransactionRequest> transactionValidator;
    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;
    private final JsonMapper jsonMapper;

    private void validate(final TransactionRequest transactionRequest) {
        var result = transactionValidator.validate(transactionRequest);

        if (!result.isValid()) {
            throw new ValidationRequestException(result);
        }
    }

    private ClienteEntity getClient(final byte id) {

        final ClienteEntity clienteEntity = clienteRepository.findById(id);

        if (Objects.isNull(clienteEntity)) {
            throw new NotFoundException();
        }

        return clienteEntity;
    }

    @Override
    @Transactional
    public EnvioTransacao saveTransaction(final TransactionRequest transactionRequest, byte id) throws IOException {

        this.validate(transactionRequest);
        this.getClient(id);

        try {
            final String result = transacaoRepository.envioTransacao(id,
                    transactionRequest.descricao(),
                    Character.toLowerCase(transactionRequest.tipo()),
                    transactionRequest.valor());

            final EnvioTransacao envioTransacao = jsonMapper.readValue(result, EnvioTransacao.class);

            LogBit.info("PROCESSOR", "Transação efetuada com sucesso!", envioTransacao);

            return envioTransacao;
        } catch (final JpaSystemException exception) {
            LogBit.error("PROCESSOR", "Não foi possivel efetuar a Transação!");
            throw new NotLimitException("O cliente não tem limite para executar essa transação.");
        }
    }


    @Override
    @Transactional
    public ExtractResponse getExtract(byte id) {

        final ClienteEntity clienteEntity = this.getClient(id);

        final ExtractResponse.SaldoDTO saldoDTO = new ExtractResponse.SaldoDTO(clienteEntity.getSaldo(), LocalDateTime.now(), clienteEntity.getLimite());

        final List<ExtractResponse.TransactionDTO> transactionDTOS = new ArrayList<>();

        final List<TransactionEntity> transactionEntities = transacaoRepository.findTop10ByClienteIdOrderByDataHoraDesc(id);

        transactionEntities.forEach(transactionEntity ->
                transactionDTOS.add(
                        new ExtractResponse.TransactionDTO(transactionEntity.getValor(),
                transactionEntity.getTipo(),
                transactionEntity.getDescricao(),
                transactionEntity.getDataHora()))
        );

        return new ExtractResponse(saldoDTO, transactionDTOS);
    }

}
