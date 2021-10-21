package com.walle.code.adapter.output.row_wrapper;

import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.wraps.ReviewerRowWrap;
import lombok.NonNull;

import javax.persistence.EntityManager;

/**
 * Реализация {@link RowWrapper<ReviewerRow, ReviewerRowWrap<Integer>>}
 * В качестве обёртки выступает нагрузка по количеству задач
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */

public enum ReviewerRowTasksWrapper implements RowWrapper<ReviewerRow, ReviewerRowWrap<Integer>> {
    INSTANCE;
    public static final String QUERY = "select count(*) from t_session " +
            "where id_reviewer like :reviewerID and c_status = 0"; // fix if i wrong
    public static final String PARAM_REVIEWER_ID = "reviewerID";

    @Override
    @NonNull
    public ReviewerRowWrap<Integer> wrapRow(ReviewerRow resultSet, EntityManager entityManager) {
        return ReviewerRowWrap.of(entityManager.createNamedQuery(QUERY, Integer.class)
                .setParameter(PARAM_REVIEWER_ID, resultSet.getId())
                .getResultList()
                .stream()
                .findFirst()
                .get(), resultSet);
    }
}
