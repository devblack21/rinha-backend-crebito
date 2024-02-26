package com.devblack21.rinha.backend.crebito.processor.validator;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.PredicateBuilder;
import com.devblack21.rinha.backend.crebito.controller.dto.TransactionRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

import static br.com.fluentvalidator.predicate.ComparablePredicate.equalTo;
import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThan;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.StringPredicate.stringSizeBetween;

@Component
public class TransactionValidator extends AbstractValidator<TransactionRequest> {
    @Override
    public void rules() {

        ruleFor(TransactionRequest::valor)
                .must(greaterThan(0))
                .withMessage("campo valor deve ser maior que 0")
                .withFieldName("valor")
                .critical();

        ruleFor(TransactionRequest::tipo)
                .must(not(equalTo('\u0000')))
                .withMessage("campo tipo deve ser preenchido")
                .withFieldName("tipo")
                .critical()
                .must(acceptType())
                .withMessage("campo tipo é invalido.")
                .withFieldName("tipo");

        ruleFor(TransactionRequest::descricao)
                .must(not(stringEmptyOrNull()))
                .withMessage("campo descricao deve ser preenchido")
                .withFieldName("descricao")
                .critical()
                .must(stringSizeBetween(1, 10))
                .withMessage("campo descricao deve ter entre 1 a 10 caracteres")
                .withFieldName("descricao");

    }

    private Predicate<Character> acceptType() {
        return PredicateBuilder.from(character -> List.of('d', 'D', 'c', 'C').contains(character));
    }


}
