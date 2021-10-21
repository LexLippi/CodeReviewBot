package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.ReviewerRowMapper;
import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.adapter.output.row_wrapper.RowWrapper;
import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.output.FindProgrammingLanguageByNameOutputPort;
import com.walle.code.port.output.FindReviewerOutputPort;
import com.walle.code.wraps.ReviewerRowWrap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.awt.image.renderable.RenderableImage;
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
    public static final String QUERY = "select id_reviewer from t_reviewer_programming_language " +
            "where id_programming_language like :programmingLanguageID";
    public static final String PARAM_PROGRAMMING_LANGUAGE_ID = "programmingLanguageID";

    @NonNull
    private final EntityManager entityManager;

    @NonNull
    private final Comparator<ReviewerRowWrap<Integer>> reviewerRowComparator;

    @NotNull
    private final RowWrapper<ReviewerRow, ReviewerRowWrap<Integer>> rowWrapper;

    @NonNull
    private final RowMapper<ReviewerRow> rowMapper;

    @Override
    public Optional<ReviewerRow> findReviewer(@NotNull ProgrammingLanguageId programmingLanguageId) {
        return this.entityManager.createNamedQuery(QUERY, Tuple.class)
                .setParameter(PARAM_PROGRAMMING_LANGUAGE_ID, programmingLanguageId.getValue())
                .getResultList()
                .stream()
                .map(result -> this.rowWrapper.wrapRow(this.rowMapper.mapRow((Tuple) result), entityManager))
                .sorted(reviewerRowComparator)
                .map(result -> result.reviewerRow)
                .findFirst();
    }
}
