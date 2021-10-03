package com.walle.code.port.output;

import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.StudentRow;

import java.util.Optional;

/**
 * Компонент для поиска ученика по идентификатору пользователя в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindStudentByUserIdOutputPort {
	/**
	 * Метод поиска ученика по идентификатору пользователя в источнике данных.
	 *
	 * @param userId идентификатор пользователя
	 * @return {@link Optional}, содержащий {@link StudentRow}, если ученик найден, иначе пустой.
	 */
	Optional<StudentRow> findStudentByUserId(UserId userId);
}
