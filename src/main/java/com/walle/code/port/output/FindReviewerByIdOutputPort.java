package com.walle.code.port.output;

import com.walle.code.domain.id.ReviewerId;
import com.walle.code.dto.row.ReviewerRow;

/**
 * Компонент для поиска ревьюера по идентификатору в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindReviewerByIdOutputPort {
	/**
	 * Метод поиска ревьюера по идентификатору в источнике данных.
	 *
	 * @param id идентификатор ревьюера
	 * @return {@link ReviewerRow}
	 */
	ReviewerRow findReviewerById(ReviewerId id);
}
