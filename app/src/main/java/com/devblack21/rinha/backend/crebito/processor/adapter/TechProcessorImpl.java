package com.devblack21.rinha.backend.crebito.processor.adapter;

import com.devblack21.rinha.backend.crebito.core.processor.TechProcessor;
import com.devblack21.rinha.backend.crebito.core.repository.ClienteRepository;
import com.devblack21.rinha.backend.crebito.core.repository.TransacaoRepository;
import com.devblack21.rinha.backend.crebito.domain.Cliente;
import com.devblack21.rinha.backend.crebito.repository.entity.ClienteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TechProcessorImpl implements TechProcessor {

    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    @Override
    @Transactional()
    public void deleteAll() {
        transacaoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Override
    @Transactional()
    public Cliente saveUser(final Cliente clienteDomain) {

        var user = clienteRepository.save(new ClienteEntity(clienteDomain.getId(),
                clienteDomain.getSaldo(),
                clienteDomain.getLimite()));

        clienteDomain.setSaldo(user.getSaldo());
        clienteDomain.setLimite(user.getLimite());

        return clienteDomain;
    }
}
