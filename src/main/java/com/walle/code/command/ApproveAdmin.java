package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import lombok.*;

import java.time.Instant;

/**
 * Класс-команда с информацией для подтверждения регистрации админа.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ApproveAdmin extends Command {
	private final String nickname;

	public static ApproveAdmin of(@NonNull DiscordUserId discordUserId, @NonNull String nickname) {
		return of(Instant.now(), discordUserId, nickname);
	}

	public static ApproveAdmin of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
								  @NonNull String nickname) {
		return new ApproveAdmin(timestamp, discordUserId, nickname);
	}

	public ApproveAdmin(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId, @NonNull String nickname) {
		super(timestamp, discordUserId);
		this.nickname = nickname;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success(String nickname) {
			return Success.of(nickname);
		}

		static Result userNotFound(String nickname) {
			return UserNotFound.of(nickname);
		}

		static Result accessDenied() {
			return AccessDenied.INSTANCE;
		}

		/**
		 * Пользователь успешно добавлен в админы
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		@Value(staticConstructor = "of")
		class Success implements Result {
			private final String nickname;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapSuccess(this);
			}
		}

		/**
		 * Пользователь с указанным никнеймом не найден
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		@Value(staticConstructor = "of")
		class UserNotFound implements Result {
			private final String nickname;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapUserNotFoundResult(this);
			}
		}

		/**
		 * У пользователя нет доступа на совершение этой операции
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum AccessDenied implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapAccessDenied(this);
			}
		}

		interface Mapper<T> {
			T mapSuccess(Success result);
			T mapUserNotFoundResult(UserNotFound result);
			T mapAccessDenied(AccessDenied result);
		}
	}
}
