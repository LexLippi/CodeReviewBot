package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Базовый класс-команда.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Data
@RequiredArgsConstructor
public abstract class Command {

	/**
	 * Дата/время вызова команды.
	 */
	@NonNull
	private final Instant timestamp;

	/**
	 * Идентификатор вызывающего пользователя.
	 */
	private final DiscordUserId discordUserId;

	private final Map<String, Object> params;

	public Command(@NonNull Instant timestamp, DiscordUserId discordUserId) {
		this(timestamp, discordUserId, Map.of());
	}
}

