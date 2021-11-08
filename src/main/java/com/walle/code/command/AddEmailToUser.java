package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

/**
 * Класс-команда с информацией для добавления электронной почты к пользователю.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AddEmailToUser extends Command {

	private final String email;

	public static AddEmailToUser of(@NonNull DiscordUserId discordUserId, @NonNull String email) {
		return of(Instant.now(), discordUserId, email);
	}

	public static AddEmailToUser of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
									@NonNull String email) {
		return new AddEmailToUser(timestamp, discordUserId, email);
	}

	private AddEmailToUser(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId, @NonNull String email) {
		super(timestamp, discordUserId);
		this.email = email;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success() {
			return Success.INSTANCE;
		}

		static Result emailIsNotCorrect() {
			return EmailIsNotCorrect.INSTANCE;
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
		 * Адрес электронной почты является некорректным
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum EmailIsNotCorrect implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapEmailIsNotCorrect(this);
			}
		}

		interface Mapper<T> {
			T mapSuccess(Success result);
			T mapEmailIsNotCorrect(EmailIsNotCorrect result);
		}
	}
}
