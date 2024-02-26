package com.devblack21.rinha.backend.crebito.repository.adapter;

import com.devblack21.rinha.backend.crebito.core.repository.TransacaoRepository;
import com.devblack21.rinha.backend.crebito.repository.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface TransacaoRepositoryImpl extends JpaRepository<TransactionEntity, Byte>, TransacaoRepository {

    List<TransactionEntity> findTop10ByClienteIdOrderByDataHoraDesc(final byte id);

    @Override
    @Transactional(readOnly = false)
    @Query(value = "select public.envio_transacao(:id_cliente,:descricao,:tipo,:valor)", nativeQuery = true)
    String envioTransacao(@Param("id_cliente") final byte cliente,
                                  @Param("descricao") final String descricao,
                                  @Param("tipo") final char tipo,
                                  @Param("valor") final int valor);

    @Modifying
    @Transactional(readOnly = false)
    @Query("delete from TransactionEntity t where 1=1")
    void deleteAll();

}
