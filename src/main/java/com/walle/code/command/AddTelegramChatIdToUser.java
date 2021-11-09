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
public final class AddTelegramChatIdToUser extends Command {
	private final String telegramNickname;
	private final Long telegramChatId;

	public static AddTelegramChatIdToUser of(@NonNull String telegramNickname, @NonNull Long telegramChatId) {
		return of(null, telegramNickname, telegramChatId);
	}

	public static AddTelegramChatIdToUser of(DiscordUserId discordUserId, @NonNull String telegramNickname,
											 @NonNull Long telegramChatId) {
		return of(Instant.now(), discordUserId, telegramNickname, telegramChatId);
	}

	public static AddTelegramChatIdToUser of(@NonNull Instant timestamp, DiscordUserId discordUserId,
											 @NonNull String telegramNickname, @NonNull Long telegramChatId) {
		return new AddTelegramChatIdToUser(timestamp, discordUserId, telegramNickname, telegramChatId);
	}

	private AddTelegramChatIdToUser(@NonNull Instant timestamp, DiscordUserId discordUserId,
									@NonNull String telegramNickname, @NonNull Long telegramChatId) {
		super(timestamp, discordUserId);
		this.telegramNickname = telegramNickname;
		this.telegramChatId = telegramChatId;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success() {
			return Success.INSTANCE;
		}

		static Result requestForConnectNotFound() {
			return RequestForConnectNotFound.INSTANCE;
		}

		/**
		 * Успешное выполнение операции
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
		 * Запрос на получение уведомлений в Telegram не найден
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum RequestForConnectNotFound implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapRequestForConnectNotFound(this);
			}
		}

		interface Mapper<T> {
			T mapSuccess(Success result);
			T mapRequestForConnectNotFound(RequestForConnectNotFound result);
		}
	}
}
