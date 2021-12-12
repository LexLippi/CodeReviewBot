package com.walle.code.wraps;


import com.walle.code.dto.row.ReviewerRow;
import lombok.Value;

/**
 * Реализация обёртки над {@link ReviewerRow}
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class ReviewerRowWrap<T> {
    /**
     * Результат обёртки
     */
    private final T wrapValue;

    /**
     * Исходное значение
     */
    private final ReviewerRow reviewerRow;
}
