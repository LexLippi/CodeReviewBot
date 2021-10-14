package com.walle.code.comparators;

import com.walle.code.dto.row.ReviewerRow;

import java.util.Comparator;

/**
 * Реализация {@link Comparator<ReviewerRow>} через сравнение по количеству строк кода
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */

public class ReviewerByCountOfCodeStringsComparator implements Comparator<ReviewerRow> {

    @Override
    public int compare(ReviewerRow o1, ReviewerRow o2) {
        // Закоментировано, т.к. пока не реализовано
        /*
           if (o1.StringCount < o2.StringCount) return -1;
           if (o1.StringCount == o2.StringCount) return 0;
           return 1;
         */
        return 0;
    }
}
