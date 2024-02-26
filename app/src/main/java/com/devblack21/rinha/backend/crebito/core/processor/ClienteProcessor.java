package com.devblack21.rinha.backend.crebito.core.processor;

import com.devblack21.rinha.backend.crebito.controller.dto.ExtractResponse;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.domain.EnvioTransacao;

import java.io.IOException;

public interface ClienteProcessor {

    EnvioTransacao saveTransaction(final TransactionRequest transactionRequest, final byte id) throws IOException;

    ExtractResponse getExtract(final byte id);

}
