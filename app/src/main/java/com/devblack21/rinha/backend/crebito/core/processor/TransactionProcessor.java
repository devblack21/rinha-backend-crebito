package com.devblack21.rinha.backend.crebito.core.processor;

import com.devblack21.rinha.backend.crebito.domain.TransactionDomain;

public interface TransactionProcessor {

    void process(TransactionDomain transactionDomain);

}
