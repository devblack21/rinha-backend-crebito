package com.devblack21.rinha.backend.crebito.repository.adapter;

import com.devblack21.rinha.backend.crebito.core.repository.TransactionRepository;
import com.devblack21.rinha.backend.crebito.repository.entity.TransactionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepositoryImpl extends MongoRepository<TransactionEntity, Byte>, TransactionRepository { }
