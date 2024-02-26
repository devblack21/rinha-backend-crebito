package com.devblack21.rinha.backend.crebito.core.repository;

import com.devblack21.rinha.backend.crebito.repository.entity.ClienteEntity;

public interface ClienteRepository {

    ClienteEntity save(final ClienteEntity clienteEntity);
    ClienteEntity findById(final byte id);
    void deleteAll();
}

