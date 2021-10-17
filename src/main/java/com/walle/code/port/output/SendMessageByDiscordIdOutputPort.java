package com.walle.code.port.output;

import com.walle.code.domain.id.DiscordUserId;

/**
 * Компонент для отправки сообщения по идентификатору пользователя в Discord.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface SendMessageByDiscordIdOutputPort {
	/**
	 * Метод отправки сообщения по идентификатору пользователя в Discord.
	 * @param discordUserId идентификатор пользователя в Discord
	 * @param message текстовое сообщение
	 */
	void sendMessageByDiscordId(DiscordUserId discordUserId, String message);
}
