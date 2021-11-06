package com.walle.code.comparators;

import com.walle.code.wraps.ReviewerRowWrap;

import java.util.Comparator;

/**
 * Реализация {@link Comparator<ReviewerRowWrap<Integer>>} через сравнение обёрточного значения
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */
public enum ReviewerRowWrapValueIncreaseComparator implements Comparator<ReviewerRowWrap<Integer>> {
    INSTANCE;

    @Override
    public int compare(ReviewerRowWrap<Integer> o1, ReviewerRowWrap<Integer> o2) {
        return o1.getWrap_value().compareTo(o2.getWrap_value());
    }
}
