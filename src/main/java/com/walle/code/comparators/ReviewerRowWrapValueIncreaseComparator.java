package com.walle.code.comparators;

import com.walle.code.wraps.ReviewerRowWrap;

import java.util.Comparator;

/**
 * Реализация {@link Comparator<ReviewerRowWrap<Integer>>} через сравнение обёрточного значения
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */

public class ReviewerRowWrapValueIncreaseComparator implements Comparator<ReviewerRowWrap<Integer>> {

    @Override
    public int compare(ReviewerRowWrap<Integer> o1, ReviewerRowWrap<Integer> o2) {
        if (o1.getWrap_value() < o2.getWrap_value())
            return -1;
        if (o1.getWrap_value() == o2.getWrap_value())
            return 0;
        return 1;
    }
}
