package com.devblack21.rinha.backend.crebito.processor.adapter;

import com.devblack21.rinha.backend.crebito.core.processor.TransactionProcessor;
import com.devblack21.rinha.backend.crebito.domain.TransactionDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionProcessorImpl implements TransactionProcessor {


    @Override
    @Transactional
    public void process(TransactionDomain transactionDomain) {



    }
}
