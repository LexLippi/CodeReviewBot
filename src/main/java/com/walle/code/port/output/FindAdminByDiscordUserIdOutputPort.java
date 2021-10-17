package com.walle.code.port.output;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.dto.row.AdminRow;

import java.util.Optional;

/**
 * Компонент для поиска администратора по идентификатору пользователя из Discord в источнике данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindAdminByDiscordUserIdOutputPort {
	/**
	 * Метод поиска администратора по идентификатору пользователя из Discord в источнике данных
	 * @param discordUserId идентификатор пользователя в Discord
	 * @return              {@link Optional}, содержащий {@link AdminRow}, если существует администратор с заданным
	 *                      идентификатором пользователя из Discord, иначе пустой
	 */
	Optional<AdminRow> findAdminByDiscordUserId(DiscordUserId discordUserId);
}
