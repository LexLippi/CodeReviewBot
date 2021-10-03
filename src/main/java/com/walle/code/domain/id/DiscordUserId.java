package com.walle.code.domain.id;

import lombok.Value;

/**
 * Компонент, представляющий идентификатор пользователя в Discord.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class DiscordUserId {
	/**
	 * Значение
	 */
	private final String value;
}
