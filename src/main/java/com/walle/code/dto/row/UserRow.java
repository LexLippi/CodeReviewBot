package com.walle.code.dto.row;

import com.walle.code.domain.entity.Email;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.domain.id.UserId;
import lombok.Value;

/**
 * Компонент, описывающий представление пользователя в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class UserRow {
	/**
	 * Идентификатор
	 */
	private final UserId id;

	/**
	 * Идентификатор пользователя в Discord
	 */
	private final DiscordUserId discordId;

	/**
	 * Никнейм пользователя
	 */
	private final String nickname;

	/**
	 * Фамилия пользователя
	 */
	private final String surname;

	/**
	 * Имя пользователя
	 */
	private final String firstName;

	/**
	 * Электронная почта пользователя
	 */
	private final Email email;

}
