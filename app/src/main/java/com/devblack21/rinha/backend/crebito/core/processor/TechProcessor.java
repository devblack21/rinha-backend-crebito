package com.devblack21.rinha.backend.crebito.core.processor;

import com.devblack21.rinha.backend.crebito.domain.Cliente;

public interface TechProcessor {

    void deleteAll();

    Cliente saveUser(final Cliente userAccountDomain);

}
