package com.walle.code.dto.row;

import com.walle.code.domain.id.ReviewerId;
import com.walle.code.domain.id.UserId;
import lombok.Value;

/**
 * Компонент, описывающий представление ревьюера в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class ReviewerRow {
	/**
	 * Идентификатор
	 */
	private final ReviewerId id;

	/**
	 * Идентификатор пользователя
	 */
	private final UserId userId;
}
