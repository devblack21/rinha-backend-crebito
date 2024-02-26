package com.devblack21.rinha.backend.crebito.core.repository;

import com.devblack21.rinha.backend.crebito.repository.entity.TransactionEntity;

import java.util.List;

public interface TransacaoRepository {

    String envioTransacao(final byte cliente,
                                  final String descricao,
                                  final char tipo,
                                  final int valor);

    List<TransactionEntity> findTop10ByClienteIdOrderByDataHoraDesc(final byte id);

    void deleteAll();
}

