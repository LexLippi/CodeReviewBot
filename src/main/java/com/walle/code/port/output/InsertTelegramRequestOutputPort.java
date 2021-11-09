package com.walle.code.port.output;

import com.walle.code.domain.id.UserId;

/**
 * Компонент для вставки запроса на подключение Telegram в источник данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface InsertTelegramRequestOutputPort {
	/**
	 * Метод вставки запроса на подключение Telegram в источник данных
	 *
	 * @param userId           идентификатор пользователя
	 * @param telegramNickname никнейм в Telegram
	 */
	void insertTelegramRequest(UserId userId, String telegramNickname);
}
