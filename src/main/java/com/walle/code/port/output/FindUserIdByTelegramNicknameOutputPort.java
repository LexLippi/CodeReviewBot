package com.walle.code.port.output;

import com.walle.code.domain.id.UserId;

import java.util.Optional;

/**
 * Компонент для поиска идентификатора пользователя по никнейму в Telegram в источнике данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindUserIdByTelegramNicknameOutputPort {
	/**
	 * Метод поиска идентификатора пользователя по никнейму в Telegram в источнике данных
	 *
	 * @param telegramNickname никнейм в Telegram
	 * @return {@link Optional}, содержащий {@link UserId}, если такой запрос найден иначе пустой.
	 */
	Optional<UserId> findUserIdByTelegramNicknameOutputPort(String telegramNickname);
}
