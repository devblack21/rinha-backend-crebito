package com.devblack21.rinha.backend.crebito.bdd.steps;

import com.devblack21.rinha.backend.crebito.bdd.core.TestContext;
import com.devblack21.rinha.backend.crebito.bdd.service.EventProducer;
import com.devblack21.rinha.backend.crebito.controller.dto.ExtractResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ParameterType;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.github.devblack21.logging.LogBit;
import io.github.devblack21.logging.configuration.LogBitConfiguration;
import io.github.devblack21.logging.enginer.DefaultEngineLogBit;
import io.github.devblack21.logging.enginer.EngineLogBit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class BaseStep {

    private final EventProducer eventProducer;

    private final TestContext testContext;


    @BeforeAll
    public static void beforeAll() {
        final LogBitConfiguration logBitConfiguration = new LogBitConfiguration("Rinha-Backend",
                "DevBlack21", "Api");
        final EngineLogBit abstractEngineBitLogger = new DefaultEngineLogBit(logBitConfiguration);
        LogBit.configure(abstractEngineBitLogger);
    }
    @Before
    public void before() {
        this.eventProducer.userDeleteAll();
        this.eventProducer.trasactionsDeleteAll();
        this.testContext.setUuid(UUID.randomUUID());
        this.testContext.setUserId(0);
        LogBit.logInfoStart("BASE_STEP_INIT", "Inicializando BDD..", testContext.getUuid());
    }

    @After
    public void after() {
        LogBit.logInfoFinish("BASE_STEP_FINISH", "Finalizando BDD..");
    }


    public static String convertTipo(final String tipo) {

        String retorno = null;

        if (StringUtils.isNotBlank(tipo)) {
            retorno = switch (tipo.toLowerCase()) {
                case "débito", "debito" -> "d";
                case "crédito", "credito" -> "c";
                case "null" -> null;
                default -> tipo;
            };
        }

        return retorno;
    }


    public static String filter(final String value) {
        return "null".equals(value) ? null : value;
    }

    @ParameterType(".*")
    public Integer integerValue(final String value) {
        return "null".equals(value) ? null : Integer.parseInt(value);
    }

    @SneakyThrows
    @Dado("que tenho um cliente com saldo de {int} e limite de {int}")
    public void addClient(final int saldo, final int limite) {
        this.testContext.setSaldo(saldo);
        this.testContext.setLimite(limite);
        this.eventProducer.createUser(this.testContext);
    }

    @Dado("que não tenho nenhum cliente cadastrado")
    public void nenhumClienteCadastrado() {
        this.eventProducer.userDeleteAll();
    }

    @SneakyThrows
    @E("envio um cadastro de transação de {string} no valor de {integerValue}")
    public void envioTransacaoCredito(final String tipo, final Integer valor) {
        this.testContext.setDescricao(this.testContext.getUuid().toString().substring(0,9));
        this.postTransaction(convertTipo(tipo), valor);
    }

    @SneakyThrows
    @Quando("a descricao for {string}")
    public void envioTransacaoCredito(final String descricao) {
        this.testContext.setDescricao(filter(descricao));
    }


    @SneakyThrows
    private void postTransaction(final String transactionType, final Integer valor) {
        this.testContext.setValor(valor);
        this.testContext.setTipo(transactionType);
        var response = this.eventProducer.postTransaction(this.testContext);
        this.testContext.setTransactionResponse(response);
    }

    @SneakyThrows
    @E("envio uma lista de transações")
    public void envioConcorrenteTransacaoDebito(final DataTable dataTable) {

        final List<TestContext> contexts = new ArrayList<>();

        for (var cell : dataTable.cells()) {
            final TestContext testContextTemp = new TestContext();
            testContextTemp.setUserId(this.testContext.getUserId());
            testContextTemp.setUuid(this.testContext.getUuid());
            this.testContext.setDescricao(this.testContext.getUuid().toString().substring(0,9));
            testContextTemp.setValor(Integer.parseInt(cell.get(0)));
            testContextTemp.setTipo(cell.get(1));
            contexts.add(testContextTemp);
        }

        contexts.stream().parallel().forEach(context -> new Thread(() -> eventProducer.postTransaction(context)).run());
    }

    @E("consulto o extrato do cliente")
    public void consultaExtratoCliente() {
        var response = this.eventProducer.getExtract(this.testContext);
        this.testContext.setExtractResponse(response);
    }

    @Entao("valido se a transação foi realizada")
    public void validaTransacaoRealizada() {
        assertThat(this.testContext.getTransactionResponse(), notNullValue());
        assertThat(this.testContext.getTransactionResponse().getStatusCode().value(), equalTo(200));
    }

    @Entao("valido que a transação não foi realizada")
    public void validaTransacaoNaoRealizada() {
        assertThat(this.testContext.getTransactionResponse(), notNullValue());
        assertThat(this.testContext.getTransactionResponse().getStatusCode().value(), not(equalTo(200)));
    }

    @Entao("valido que o status code da transacao é igual a {int}")
    public void validaStatusCodeTrasacao(final int status) {
        assertThat(this.testContext.getTransactionResponse(), notNullValue());
        assertThat(this.testContext.getTransactionResponse().getStatusCode().value(), equalTo(status));
    }

    @Entao("valido que o status code da consulta é igual a {int}")
    public void validaStatusCodeExtrato(final int status) {
        assertThat(this.testContext.getTransactionResponse(), notNullValue());
        assertThat(this.testContext.getTransactionResponse().getStatusCode().value(), equalTo(status));
    }

    @Entao("valido a conformidade dos dados no extrato")
    public void validaConformidadeExtrato() {

        final ResponseEntity<?> responseEntity = this.testContext.getExtractResponse();

        assertThat(responseEntity.getBody(), notNullValue());
        assertThat(responseEntity.getBody(), instanceOf(ExtractResponse.class));

        final ExtractResponse extractResponse = this.testContext.getExtractResponse().getBody();

        final Map<String, Object> transactionRequest = this.testContext.getTransactionRequest();
        assertThat(extractResponse, notNullValue());
        assertThat(extractResponse.transactions().get(0).tipo(), equalTo(String.valueOf(transactionRequest.get("tipo")).charAt(0)));
        assertThat(extractResponse.transactions().get(0).descricao(), equalTo(String.valueOf(transactionRequest.get("descricao"))));
        assertThat(extractResponse.transactions().get(0).valor(), equalTo(Integer.valueOf(String.valueOf(transactionRequest.get("valor")))));
    }

    @Entao("valido que não existe a transação cadastrada no extrato")
    public void validaInexistenciaDaTransacaoNoExtrato() {

        final ExtractResponse extractResponse = this.testContext.getExtractResponse().getBody();
        final Map<String, Object> transactionRequest = this.testContext.getTransactionRequest();

        assertThat(extractResponse, notNullValue());
        assertThat(extractResponse.transactions().get(0).tipo(), not(equalTo(String.valueOf(transactionRequest.get("tipo")).charAt(0))));
        assertThat(extractResponse.transactions().get(0).descricao(), not(equalTo(String.valueOf(transactionRequest.get("descricao")))));
        assertThat(extractResponse.transactions().get(0).valor(), not(equalTo(Integer.valueOf(String.valueOf(transactionRequest.get("valor"))))));
    }

    @Entao("valido no extrato se o saldo do cliente é igual a {int}")
    public void validaSaldoCliente(final int saldo) {

        assertThat(this.testContext.getExtractResponse(), notNullValue());

        final ExtractResponse extractResponse = this.testContext.getExtractResponse().getBody();

        assertThat(extractResponse, notNullValue());
        assertThat(extractResponse.saldo().total(), equalTo(saldo));
    }

}
