package com.walle.code.port.output;

import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.UserRow;

import java.util.Collection;
import java.util.List;

/**
 * Компонент для поиска списка пользователей по коллекции идентификаторов в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindUserByIdInOutputPort {
	/**
	 * Метод поиска списка пользователей по коллекции идентификаторов в источнике данных.
	 *
	 * @param ids коллекция идентификаторов пользователей
	 * @return список {@link UserRow}
	 */
	List<UserRow> findUserByIdIn(Collection<UserId> ids);
}
