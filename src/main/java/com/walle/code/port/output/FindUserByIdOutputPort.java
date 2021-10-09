package com.walle.code.port.output;

import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.UserRow;

/**
 * Компонент для поиска пользователя по идентификатору в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindUserByIdOutputPort {
	/**
	 * Метод поиска пользователя по идентификатору в источнике данных.
	 *
	 * @param id идентификатор пользователя
	 * @return {@link UserRow}
	 */
	UserRow findUserById(UserId id);
}
