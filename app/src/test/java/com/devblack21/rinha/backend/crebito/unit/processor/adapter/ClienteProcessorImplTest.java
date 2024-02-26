package com.devblack21.rinha.backend.crebito.unit.processor.adapter;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.context.ValidationResult;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.core.exception.NotFoundException;
import com.devblack21.rinha.backend.crebito.core.exception.NotLimitException;
import com.devblack21.rinha.backend.crebito.core.repository.ClienteRepository;
import com.devblack21.rinha.backend.crebito.core.repository.TransacaoRepository;
import com.devblack21.rinha.backend.crebito.processor.adapter.ClienteProcessorImpl;
import com.devblack21.rinha.backend.crebito.repository.entity.ClienteEntity;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.devblack21.logging.LogBit;
import io.github.devblack21.logging.configuration.LogBitConfiguration;
import io.github.devblack21.logging.enginer.DefaultEngineLogBit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.orm.jpa.JpaSystemException;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClienteProcessorImplTest {

    @Mock
    private ClienteRepository userAccountRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private AbstractValidator<TransactionRequest> transactionValidator;

    @Mock
    private JsonMapper jsonMapper;

    @InjectMocks
    private ClienteProcessorImpl userAccountProcessor;

    @Before
    public void before() {
        LogBit.configure(new DefaultEngineLogBit(new LogBitConfiguration("",
                "",
                "")));
    }

    @Test
    public void shouldDebitoProcess() throws IOException {

        final ClienteEntity userAccountEntity = new ClienteEntity((byte) 1, 100, 0);

        when(transactionValidator.validate(any(TransactionRequest.class))).thenReturn(ValidationResult.ok());

        when(userAccountRepository.findById(anyByte()))
                .thenReturn(userAccountEntity);

        when(transacaoRepository.envioTransacao(anyByte(), anyString(), anyChar(), anyInt()))
                .thenReturn("{\"id\" : 1, \"limite\" : 100, \"saldo\" : 1}");

        userAccountProcessor.saveTransaction(new TransactionRequest(1, 'd', "descricao"), (byte) 1);
    }

    @Test
    public void shouldDebitoProcessWhenClienteDontHaveLimit() {

        final ClienteEntity userAccountEntity = new ClienteEntity((byte) 1, 100, 0);

        when(transactionValidator.validate(any(TransactionRequest.class))).thenReturn(ValidationResult.ok());

        when(userAccountRepository.findById(anyByte()))
                .thenReturn(userAccountEntity);

        when(transacaoRepository.envioTransacao(anyByte(), anyString(), anyChar(), anyInt()))
                .thenThrow(JpaSystemException.class);

        assertThrows(NotLimitException.class,
                () -> userAccountProcessor.saveTransaction(new TransactionRequest(101, 'd', "descricao"), (byte) 1));
    }

    @Test
    public void shouldCreditoProcess() throws IOException {

        when(transactionValidator.validate(any(TransactionRequest.class))).thenReturn(ValidationResult.ok());

        when(userAccountRepository.findById(anyByte()))
                .thenReturn(new ClienteEntity((byte) 1, 100, 0));

        when(transacaoRepository.envioTransacao(anyByte(), anyString(), anyChar(), anyInt()))
                .thenReturn("{}");

        userAccountProcessor.saveTransaction(new TransactionRequest(1, 'c', "descricao"), (byte) 1);
    }

    @Test
    public void shouldExtract() {

        when(userAccountRepository.findById(anyByte()))
                .thenReturn(new ClienteEntity((byte) 1, 100, 0));

        when(transacaoRepository.findTop10ByClienteIdOrderByDataHoraDesc(anyByte()))
                .thenReturn(List.of());

        userAccountProcessor.getExtract((byte) 1);
    }

    @Test
    public void shouldNotExtractWheClientNotExists() {

        when(userAccountRepository.findById(anyByte()))
                .thenReturn(null);

        assertThrows(NotFoundException.class,
                () -> userAccountProcessor.getExtract((byte) 1));
    }


}
