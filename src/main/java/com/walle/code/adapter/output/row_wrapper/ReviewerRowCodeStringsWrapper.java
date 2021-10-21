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
    /** @todo:
     * нужно (как я понимаю) получить набор всех тасков из t_session
     * Где наш ревьюер в роли ревьюера и статус в процессе
     * Потом объединить по id_session с t_task
     * А затем подсчитать количество строк кода
     */
    public static final String QUERY = ""; // fix if i wrong
    public static final String PARAM_REVIEWER_ID = "programmingLanguageID";

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
