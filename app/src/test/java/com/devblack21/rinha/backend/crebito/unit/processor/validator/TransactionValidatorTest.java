package com.devblack21.rinha.backend.crebito.unit.processor.validator;

import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import com.devblack21.rinha.backend.crebito.processor.validator.TransactionValidator;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TransactionValidatorTest {

    private final TransactionValidator transactionValidator = new TransactionValidator();

    @Test
    public void shouldIsInvalidateWhenValorIsNullOrZero() {
       var result = transactionValidator.validate(new TransactionRequest(0, 'c', "descricao"));
       assertThat(result.isValid(), equalTo(Boolean.FALSE));
    }

    @Test
    public void shouldIsValidateWhenValorIsNotNullOrZero() {
        var result = transactionValidator.validate(new TransactionRequest(1, 'c', "descricao"));
        assertThat(result.isValid(), equalTo(Boolean.TRUE));
    }

    @Test
    public void shouldIsInvalidateWhenTipoIsNull() {
        var result = transactionValidator.validate(new TransactionRequest(1, '\u0000', "descricao"));
        assertThat(result.isValid(), equalTo(Boolean.FALSE));
    }

    @Test
    public void shouldIsInvalidateWhenTipoDiffCreditoOrDebito() {

        final List<Character> characters = List.of('a', 'b', 'e', 'f', 'g',
                'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '0',
                '1', '2', '3', '4', '5',
                '6', '7', '8', '9');

        for (char value : characters) {
            assertThat(transactionValidator.validate(new TransactionRequest(1, value, "descricao")).isValid(),
                    equalTo(Boolean.FALSE));

            assertThat(transactionValidator.validate(
                    new TransactionRequest(1, Character.toUpperCase(value), "descricao")
                    ).isValid(), equalTo(Boolean.FALSE));
        }

    }

    @Test
    public void shouldIsValidateWhenTipoCreditoOrDebito() {
        assertThat(transactionValidator.validate(new TransactionRequest(1, 'c', "descricao")).isValid(),
                equalTo(Boolean.TRUE));

        assertThat(transactionValidator.validate(new TransactionRequest(1, 'd', "descricao")).isValid(),
                equalTo(Boolean.TRUE));
    }

    @Test
    public void shouldIsInvalidateWhenDescricaoIsNull() {
        var result = transactionValidator.validate(new TransactionRequest(1, 'c', null));
        assertThat(result.isValid(), equalTo(Boolean.FALSE));
    }

    @Test
    public void shouldIsInvalidateWhenDescricaoWhenGrantherThen10Caracteres() {
        var result = transactionValidator.validate(new TransactionRequest(1, 'c', "abcdefghijk"));
        assertThat(result.isValid(), equalTo(Boolean.FALSE));
    }

    @Test
    public void shouldIsValidateWhenDescricaoWhenBetween1And10Caracteres() {
        assertThat(transactionValidator.validate(new TransactionRequest(1, 'c', "abcdefghij"))
                .isValid(), equalTo(Boolean.TRUE));

        assertThat(transactionValidator.validate(new TransactionRequest(1, 'c', "abcde"))
                .isValid(), equalTo(Boolean.TRUE));

        assertThat(transactionValidator.validate(new TransactionRequest(1, 'c', "a"))
                .isValid(), equalTo(Boolean.TRUE));
    }

}
