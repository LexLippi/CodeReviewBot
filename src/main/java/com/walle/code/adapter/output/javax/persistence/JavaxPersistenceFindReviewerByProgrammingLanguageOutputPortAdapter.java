package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.adapter.output.row_wrapper.RowWrapper;
import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.output.FindReviewerOutputPort;
import com.walle.code.wraps.ReviewerRowWrap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link FindReviewerOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindReviewerByProgrammingLanguageOutputPortAdapter implements FindReviewerOutputPort {
    public static final String QUERY = "select trp.id_reviewer, tr.id_user from t_reviewer_programming_language trp " +
            "inner join t_reviewer tr on trp.id_reviewer = tr.id" +
            "where trp.id_programming_language = :programmingLanguageID";
    public static final String PARAM_PROGRAMMING_LANGUAGE_ID = "programmingLanguageID";

    @NonNull
    private final EntityManager entityManager;

    @NonNull
    private final Comparator<ReviewerRowWrap<Integer>> reviewerRowComparator;

    @NonNull
    private final List<RowWrapper<ReviewerRow, ReviewerRowWrap<Integer>>> rowWrappers;

    @NonNull
    private final RowMapper<ReviewerRow> rowMapper;

    @Override
    @NonNull
    public Optional<ReviewerRow> findReviewer(@NonNull ProgrammingLanguageId programmingLanguageId) {
        var resultStream = this.entityManager.createNativeQuery(QUERY, Tuple.class)
                .setParameter(PARAM_PROGRAMMING_LANGUAGE_ID, programmingLanguageId.getValue())
                .getResultList()
                .stream();
        for (var rowWrapper : rowWrappers) {
            var sortedListOfReviewers = resultStream.map(result ->
                    rowWrapper.wrapRow(this.rowMapper.mapRow((Tuple) result), entityManager))
                    .sorted(reviewerRowComparator);
            var optimal = sortedListOfReviewers.findAny();

            if (sortedListOfReviewers.count() <= 1 ||
                reviewerRowComparator.compare(((ReviewerRowWrap<Integer>) optimal.get()),
                ((ReviewerRowWrap<Integer>)sortedListOfReviewers.skip(1).findAny().get())) == 0)
                return optimal;
        }
        return resultStream.map(result -> rowWrappers.get(1)
                        .wrapRow(this.rowMapper.mapRow((Tuple) result), entityManager))
                .min(reviewerRowComparator)
                .map(result -> ((ReviewerRowWrap<Integer>)result).reviewerRow);
    }
}
