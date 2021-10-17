package com.walle.code.port.output;

import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.UserRow;

/**
 * Компонент для вставки пользователя в источник данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface InsertUserOutputPort {
	/**
	 * Метод вставки пользователя в источник данных
	 *
	 * @param user информация о пользователе, которую необходимо вставить в источник данных
	 * @return     идентификатор пользователя, полученный при вставке в источник данных
	 */
	UserId insertUser(UserRow user);
}
