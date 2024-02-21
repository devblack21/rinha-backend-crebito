package com.devblack21.rinha.backend.crebito.bdd.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.github.devblack21.logging.LogBit;

public class BaseStep {

    @BeforeStep
    public static void before() {
        LogBit.logInfoStart("BASE_STEP_INIT", "Inicializando BDD..");
    }

    @AfterStep
    public static void after() {
        LogBit.logInfoFinish("BASE_STEP_FINISH", "Finalizando BDD..");
    }

    @Dado("que tenho um cliente com saldo de {int} e limite de {int}")
    public void addClient(final int saldo, final int limite) {

    }

    @E("envio um cadastro de transação de débito no valor de {int}")
    public void envioTransacaoDebito(final int valor) {

    }

    @E("envio um cadastro de transação de crédito no valor de {int}")
    public void envioTransacaoCredito(final int valor) {

    }

    @E("envio uma lista de transações")
    public void envioTransacaoDebito(final DataTable dataTable) {

    }

    @Entao("valido que a transação não foi realizada")
    public void validaTransacaoNaoRealizada() {

    }

    @Entao("valido se a transação foi realizada")
    public void validaTransacaoRealizada() {

    }

    @E("valido a conformidade dos dados no extrato")
    public void validaConformidadeExtrato() {

    }

    @Entao("valido no extrato se o saldo do cliente é igual a {int}")
    public void validaSaldoCliente(final int saldo) {

    }

}
