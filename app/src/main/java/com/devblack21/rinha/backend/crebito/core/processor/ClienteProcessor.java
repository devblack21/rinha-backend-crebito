package com.devblack21.rinha.backend.crebito.core.processor;

import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.domain.EnvioTransacao;
import com.devblack21.rinha.backend.crebito.domain.Extrato;

import java.io.IOException;

public interface ClienteProcessor {

    EnvioTransacao saveTransaction(final TransactionRequest transactionRequest, final byte id) throws IOException;

    Extrato getExtract(final byte id) throws IOException;

}
