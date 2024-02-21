package com.devblack21.rinha.backend.crebito.core.repository;

import com.devblack21.rinha.backend.crebito.repository.entity.UserAccountEntity;

import java.util.Set;

public interface UserAccountRepository {
    UserAccountEntity save(final UserAccountEntity userAccountEntity);
    UserAccountEntity findBy_id(final byte id);
}
