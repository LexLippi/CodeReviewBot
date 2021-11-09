package com.walle.code.port.output;

import com.walle.code.domain.id.UserId;

/**
 * Компонент для обновления идентификатора чата в Telegram пользователя по идентификатору в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface UpdateUserChatIdByIdOutputPort {
	/**
	 * Метод обновления идентификатора чата в Telegram пользователя по идентификатору в источнике данных.
	 *
	 * @param userId идентификатор пользователя
	 * @param chatId идентификатор чата в Telegram
	 */
	void updateUserChatIdById(UserId userId, Long chatId);
}
