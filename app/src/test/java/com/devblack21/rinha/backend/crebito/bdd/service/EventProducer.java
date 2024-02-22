package com.devblack21.rinha.backend.crebito.bdd.service;

import com.devblack21.rinha.backend.crebito.bdd.core.TestContext;
import com.devblack21.rinha.backend.crebito.controller.dto.ExtractResponse;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionResponse;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventProducer {

    private static final String URL = "http://localhost:20000/clientes";

    final RestTemplate restTemplate = new RestTemplate();

    public TransactionResponse createUser(final TestContext testContext) {

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(URL);
        uriComponentsBuilder.pathSegment("user");
        uriComponentsBuilder.queryParam("id", testContext.getUserId());
        uriComponentsBuilder.queryParam("saldo", testContext.getSaldo());
        uriComponentsBuilder.queryParam("limite", testContext.getLimite());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        final HttpEntity<TransactionRequest> request = new HttpEntity<>(headers);

        final ResponseEntity<TransactionResponse> response = restTemplate
                .exchange(uriComponentsBuilder.build().toUriString(), HttpMethod.POST, request, TransactionResponse.class);

        return response.getBody();
    }

    public void userDeleteAll() {

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(URL);
        uriComponentsBuilder.pathSegment("user", "delete-all");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        final HttpEntity<TransactionRequest> request = new HttpEntity<>(headers);

        final ResponseEntity<TransactionResponse> response = restTemplate
                .exchange(uriComponentsBuilder.build().toUriString(), HttpMethod.DELETE, request, TransactionResponse.class);

    }

    public void trasactionsDeleteAll() {

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(URL);
        uriComponentsBuilder.pathSegment("trasacoes", "delete-all");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        final HttpEntity<TransactionRequest> request = new HttpEntity<>(headers);

        final ResponseEntity<TransactionResponse> response = restTemplate
                .exchange(uriComponentsBuilder.build().toUriString(), HttpMethod.DELETE, request, TransactionResponse.class);

    }

    @Async
    public ResponseEntity<TransactionResponse> postTransaction(final TestContext testContext) {

        final RestTemplate restTemplate = new RestTemplate();

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(URL);
        uriComponentsBuilder.pathSegment(String.valueOf(testContext.getUserId()), "transacoes");

        Map<String, Object> json = new HashMap<>();
        json.put("valor", testContext.getValor());
        json.put("tipo", testContext.getTipo());
        json.put("descricao", testContext.getDescricao());

        testContext.setTransactionRequest(json);

        final HttpEntity<Map> request = new HttpEntity<>(json);

        try {
            return restTemplate
                    .exchange(uriComponentsBuilder.build().toUriString(), HttpMethod.POST, request, TransactionResponse.class);
        } catch (HttpStatusCodeException errorException) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(errorException.getStatusCode(), errorException.getMessage())).build();
        }

    }

    public ResponseEntity<ExtractResponse> getExtract(final TestContext testContext) {

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(URL);
        uriComponentsBuilder.pathSegment(String.valueOf(testContext.getUserId()), "extrato");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        final HttpEntity<TransactionRequest> request = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(uriComponentsBuilder.build().toUriString(), HttpMethod.GET, request, ExtractResponse.class);
        } catch (HttpClientErrorException errorException) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(errorException.getStatusCode(), errorException.getMessage())).build();
        }
    }

}
