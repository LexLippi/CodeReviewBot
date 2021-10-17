package com.walle.code.dto.row;

import com.walle.code.domain.id.AdminId;
import com.walle.code.domain.id.UserId;
import lombok.Value;

/**
 * Компонент, описывающий представление администратора в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class AdminRow {
	/**
	 * Идентификатор
	 */
	private final AdminId id;

	/**
	 * Идентификатор пользователя
	 */
	private final UserId userId;
}
