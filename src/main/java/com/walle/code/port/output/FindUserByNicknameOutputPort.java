package com.walle.code.port.output;

import com.walle.code.dto.row.UserRow;

import java.util.Optional;

/**
 * Компонент для поиска пользователя по никнейму в источнике данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindUserByNicknameOutputPort {
	/**
	 * Метод поиска пользователя по никнейму в источнике данных
	 *
	 * @param nickname никнейм
	 * @return         {@link Optional}, содержащий {@link UserRow}, если существует пользователь с заданным никнеймом,
	 *                 иначе пустой
	 */
	Optional<UserRow> findUserByNickname(String nickname);
}
