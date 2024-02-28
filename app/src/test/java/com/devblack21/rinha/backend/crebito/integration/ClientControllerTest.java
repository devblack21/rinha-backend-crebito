package com.devblack21.rinha.backend.crebito.integration;

import br.com.fluentvalidator.context.ValidationResult;
import com.devblack21.rinha.backend.crebito.controller.ClienteController;
import com.devblack21.rinha.backend.crebito.controller.dto.ExtractResponse;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.controller.handler.ExceptionControllerHandler;
import com.devblack21.rinha.backend.crebito.core.exception.NotFoundException;
import com.devblack21.rinha.backend.crebito.core.exception.NotLimitException;
import com.devblack21.rinha.backend.crebito.core.exception.ValidationRequestException;
import com.devblack21.rinha.backend.crebito.core.processor.ClienteProcessor;
import com.devblack21.rinha.backend.crebito.domain.EnvioTransacao;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    @MockBean
    private MockMvc mockMvc;
    @Mock
    private ClienteProcessor clienteProcessor;
    @InjectMocks
    private ClienteController controller;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionControllerHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    public void shouldSaveTransactionWithSuccess() throws Exception {

        final Map<String, Object> json = new HashMap<>();
        json.put("valor", 1);
        json.put("tipo", "d");
        json.put("descricao", "a");

        when(clienteProcessor.saveTransaction(any(TransactionRequest.class), anyByte()))
                .thenReturn(new EnvioTransacao((byte) 1, 1, 1));

        this.mockMvc.perform(post("/clientes/1/transacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(json)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveTransactionNotSuccessBecauseClienteNotFound() throws Exception {

        final Map<String, Object> json = new HashMap<>();
        json.put("valor", 1);
        json.put("tipo", "d");
        json.put("descricao", "a");

        when(clienteProcessor.saveTransaction(any(TransactionRequest.class), anyByte()))
                .thenThrow(new NotFoundException());

        this.mockMvc.perform(post("/clientes/1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JsonMapper().writeValueAsString(json)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldSaveTransactionNotSuccessBecauseClienteNotHaveLimit() throws Exception {

        final Map<String, Object> json = new HashMap<>();
        json.put("valor", 1);
        json.put("tipo", "d");
        json.put("descricao", "a");

        when(clienteProcessor.saveTransaction(any(TransactionRequest.class), anyByte()))
                .thenThrow(new NotLimitException(""));

        this.mockMvc.perform(post("/clientes/1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JsonMapper().writeValueAsString(json)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldSaveTransactionNotSuccessWhenFieldsIsInvalids() throws Exception {

        final Map<String, Object> json = new HashMap<>();
        json.put("valor", 1);
        json.put("tipo", null);
        json.put("descricao", null);

        when(clienteProcessor.saveTransaction(any(TransactionRequest.class), anyByte()))
                .thenThrow(new ValidationRequestException(ValidationResult.fail(List.of())));

        this.mockMvc.perform(post("/clientes/1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JsonMapper().writeValueAsString(json)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldGetExtract() throws Exception {

        when(clienteProcessor.getExtract(anyByte()))
                .thenReturn(any(ExtractResponse.class));

        this.mockMvc.perform(get("/clientes/1/extrato")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetExtractWhenClientNotExists() throws Exception {

        when(clienteProcessor.getExtract(anyByte()))
                .thenThrow(new NotFoundException());

        this.mockMvc.perform(get("/clientes/1/extrato")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetExtractWhenClientNotHaveLimit() throws Exception {

        when(clienteProcessor.getExtract(anyByte()))
                .thenThrow(new NotLimitException(""));

        this.mockMvc.perform(get("/clientes/1/extrato")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

}
