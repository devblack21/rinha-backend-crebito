package com.devblack21.rinha.backend.crebito.core.repository;

public interface TransacaoRepository {

    String envioTransacao(final byte cliente,
                                  final String descricao,
                                  final char tipo,
                                  final int valor);

    String getTransacoes(final byte cliente);

    void deleteAll();
}

