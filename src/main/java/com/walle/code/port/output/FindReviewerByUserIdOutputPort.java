package com.walle.code.port.output;

import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.ReviewerRow;

import java.util.Optional;

/**
 * Компонент для поиска ревьюера по идентификатору пользователя в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindReviewerByUserIdOutputPort {
	/**
	 * Метод поиска ревьюера по идентификатору пользователя в источнике данных.
	 *
	 * @param userId идентификатор пользователя
	 * @return {@link Optional}, содержащий {@link ReviewerRow}, если ревьюер найден, иначе пустой.
	 */
	Optional<ReviewerRow> findReviewerByUserId(UserId userId);
}
