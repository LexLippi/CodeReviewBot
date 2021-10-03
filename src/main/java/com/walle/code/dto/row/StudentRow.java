package com.walle.code.dto.row;

import com.walle.code.domain.id.StudentId;
import com.walle.code.domain.id.UserId;
import lombok.Value;

/**
 * Компонент, описывающий представление ученика в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class StudentRow {
	/**
	 * Идентификатор
	 */
	private final StudentId id;

	/**
	 * Идентификатор пользователя
	 */
	private final UserId userId;
}
