package com.walle.code.port.output;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.dto.row.UserRow;

import java.util.Optional;

/**
 * Компонент для поиска пользователя по идентификатору пользователя из Discord в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindUserByDiscordIdOutputPort {
	/**
	 * Метод поиска пользователя по идентификатору пользователя из Discord в источнике данных.
	 *
	 * @param discordId идентификатор пользователя в Discord
	 * @return {@link Optional}, содержащий {@link UserRow}, если пользователь с таким идентификатором найден,
	 * иначе пустой.
	 */
	Optional<UserRow> findUserByDiscordId(DiscordUserId discordId);
}
