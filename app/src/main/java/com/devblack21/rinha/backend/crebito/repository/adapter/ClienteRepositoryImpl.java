package com.devblack21.rinha.backend.crebito.repository.adapter;

import com.devblack21.rinha.backend.crebito.core.repository.ClienteRepository;
import com.devblack21.rinha.backend.crebito.repository.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ClienteRepositoryImpl extends JpaRepository<ClienteEntity, Byte>, ClienteRepository {

    @Modifying
    @Transactional()
    @Query("delete from ClienteEntity c where 1=1")
    void deleteAll();

    @Override
    ClienteEntity findById(final byte id);
}
