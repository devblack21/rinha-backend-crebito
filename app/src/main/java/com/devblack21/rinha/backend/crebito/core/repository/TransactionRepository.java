package com.devblack21.rinha.backend.crebito.core.repository;

import com.devblack21.rinha.backend.crebito.repository.entity.TransactionEntity;

import java.util.Set;

public interface TransactionRepository {
    TransactionEntity save(final TransactionEntity transactionEntity);
    Set<TransactionEntity> findFirst10ByUserAccountIdOrderByDateTimeDesc(final byte id);
}
