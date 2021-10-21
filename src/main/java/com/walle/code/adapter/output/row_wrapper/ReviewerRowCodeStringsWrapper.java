package com.walle.code.adapter.output.row_wrapper;

import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.wraps.ReviewerRowWrap;
import lombok.NonNull;

import javax.persistence.EntityManager;

/**
 * Реализация {@link RowWrapper< ReviewerRow ,  ReviewerRowWrap <Integer>>}
 * В качестве обёртки выступает нагрузка по количеству строк кода
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */


public enum ReviewerRowCodeStringsWrapper implements RowWrapper<ReviewerRow, ReviewerRowWrap<Integer>>{
    INSTANCE;

    public static final String QUERY = "select c_text from t_session ts inner join t_task tt" +
            "on ts.id=tt.id_session where ts.id_reviewer like :reviewerID and tt.c_status = 0"; // fix if i wrong
    public static final String PARAM_REVIEWER_ID = "reviewerID";

    @Override
    @NonNull
    public ReviewerRowWrap<Integer> wrapRow(ReviewerRow resultSet, EntityManager entityManager) {
        return ReviewerRowWrap.of(entityManager.createNamedQuery(QUERY, String.class)
                .setParameter(PARAM_REVIEWER_ID, resultSet.getId())
                .getResultList()
                .stream()
                .map(r -> r.chars().filter(c -> c == '\n').count())
                .mapToInt(r -> r.intValue())
                .sum(), resultSet);
    }
}
