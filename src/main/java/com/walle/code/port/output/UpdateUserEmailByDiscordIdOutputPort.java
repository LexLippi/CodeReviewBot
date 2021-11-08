package com.walle.code.port.output;

import com.walle.code.domain.id.DiscordUserId;

/**
 * Компонент для обновления адреса электронной почты пользователя по идентификатору пользователя в Discord в источнике
 * данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface UpdateUserEmailByDiscordIdOutputPort {
	/**
	 * Метод обновления адреса электронной почты пользователя по идентификатору пользователя в Discord в источнике
	 * данных
	 *
	 * @param email         адрес электронной почты
	 * @param discordUserId идентификатор пользователя в Discord
	 */
	void updateUserEmailByDiscordId(String email, DiscordUserId discordUserId);
}
