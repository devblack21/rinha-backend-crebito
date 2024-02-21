package com.devblack21.rinha.backend.crebito.repository.adapter;

import com.devblack21.rinha.backend.crebito.core.repository.UserAccountRepository;
import com.devblack21.rinha.backend.crebito.repository.entity.UserAccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepositoryImpl extends MongoRepository<UserAccountEntity, Byte>, UserAccountRepository { }
