package com.walle.code.port.output;

import com.walle.code.domain.id.ReviewerId;
import com.walle.code.dto.row.ReviewerRow;

/**
 * Компонент для вставки ревьюера в источник данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface InsertReviewerOutputPort {
	/**
	 * Метод вставки ревьюера в источник данных
	 *
	 * @param reviewer информация о ревьюере
	 * @return         идентификатор вставленной записи о ревьюере
	 */
	ReviewerId insertReviewer(ReviewerRow reviewer);
}
