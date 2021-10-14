package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.output.FindProgrammingLanguageByNameOutputPort;
import com.walle.code.port.output.FindReviewerOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.Optional;

/**
 * Реализация {@link FindReviewerOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */

@RequiredArgsConstructor
public class JavaxPersistenceFindReviewerByProgrammingLanguageOutputPortAdapter implements FindReviewerOutputPort {
    /* Здесь нужно будет вписать нужные аргументы :) */
    public static final String QUERY = "^__^";
    public static final String PARAM_PROGRAMMING_LANGUAGE_NAME = "^__^";

    @NonNull
    private final EntityManager entityManager;

    @NonNull
    private final Comparator<ReviewerRow> reviewerRowComparator; // Временная заглушка (т.к. ReviewerRow не подходит)

    @Override
    public Optional<ReviewerRow> findReviewer(ProgrammingLanguageId programmingLanguageId) {
        /**
         * sorta of comments just for me:
         * what i need todo:
         * first - take list of reviewers by PL
         * second - take one of comparators
         * third - sort
         * fourth - ???
         * return the first of 'em
         */
        return this.entityManager.createNamedQuery(QUERY, ReviewerRow.class)
                .setParameter(PARAM_PROGRAMMING_LANGUAGE_NAME, programmingLanguageId)
                .getResultList()
                .stream()
                .sorted(reviewerRowComparator) // <--- Временно так, нужен будет надкласс у Ревьюера
                .findFirst();
    }
}
