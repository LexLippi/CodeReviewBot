package com.walle.code.comparators;

import com.walle.code.dto.row.ReviewerRow;

import java.util.Comparator;

/**
 * Реализация {@link Comparator<ReviewerRow>} через сравнение по количеству задач
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */

public class ReviewerByCountOfTasksComparator implements Comparator<ReviewerRow> {

    @Override
    public int compare(ReviewerRow o1, ReviewerRow o2) {
        // Закоментировано, т.к. пока не реализовано
        /*
           if (o1.TasksValue < o2.TasksValue) return -1;
           if (o1.TasksValue == o2.TasksValue) return 0;
           return 1;
         */
        return 0;
    }
}
