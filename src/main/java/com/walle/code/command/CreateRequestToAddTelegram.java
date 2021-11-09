package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

/**
 * Класс-команда с информацией для добавления идентификатора чата в Telegram к пользователю.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateRequestToAddTelegram extends Command {

	private final String telegramNickname;

	public static CreateRequestToAddTelegram of(@NonNull DiscordUserId discordUserId,
												@NonNull String telegramNickname) {
		return of(Instant.now(), discordUserId, telegramNickname);
	}

	public static CreateRequestToAddTelegram of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
												@NonNull String telegramNickname) {
		return new CreateRequestToAddTelegram(timestamp, discordUserId, telegramNickname);
	}

	private CreateRequestToAddTelegram(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
									   @NonNull String telegramNickname) {
		super(timestamp, discordUserId);
		this.telegramNickname = telegramNickname;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success() {
			return Success.INSTANCE;
		}

		static Result userNotFound() {
			return UserNotFound.INSTANCE;
		}

		/**
		 * Запрос успешно создан.
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum Success implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapSuccess(this);
			}
		}

		/**
		 * Пользователь не найден
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum UserNotFound implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapUserNotFound(this);
			}
		}

		interface Mapper<T> {
			T mapSuccess(Success result);
			T mapUserNotFound(UserNotFound result);
		}
	}
}
