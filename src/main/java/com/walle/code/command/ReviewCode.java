package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.dto.status.TaskStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

/**
 * Класс-команда с информацией для комментирования сеанса код-ревью
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ReviewCode extends Command {
	/**
	 * Никнейм того, чей код был проверен
	 */
	private final String nickname;

	/**
	 * Статус ревью
	 */
	private final TaskStatus status;

	/**
	 * Текст ревью
	 */
	private final String text;

	public static ReviewCode of(@NonNull DiscordUserId discordUserId, @NonNull String nickname,
								@NonNull TaskStatus status, @NonNull String text) {
		return new ReviewCode(Instant.now(), discordUserId, nickname, status, text);
	}

	public static ReviewCode of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
								@NonNull String nickname, @NonNull TaskStatus status, @NonNull String text) {
		return new ReviewCode(timestamp, discordUserId, nickname, status, text);
	}

	private ReviewCode(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId, @NonNull String nickname,
					  @NonNull TaskStatus status, @NonNull String text) {
		super(timestamp, discordUserId);
		this.nickname = nickname;
		this.status = status;
		this.text = text;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success() {
			return Success.INSTANCE;
		}

		static Result sessionNotFound() {
			return SessionNotFound.INSTANCE;
		}

		static Result reviewerNotFound() {
			return ReviewerNotFound.INSTANCE;
		}

		static Result studentNotFound() {
			return StudentNotFound.INSTANCE;
		}

		static Result userNotFound() {
			return UserNotFound.INSTANCE;
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
		 * Сеанс код-ревью не найден
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum SessionNotFound implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapSessionNotFound(this);
			}
		}

		/**
		 * Ревьюер не найден
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum ReviewerNotFound implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapReviewerNotFound(this);
			}
		}

		/**
		 * Ученик не найден
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum StudentNotFound implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapStudentNotFound(this);
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
			T mapSessionNotFound(SessionNotFound result);
			T mapReviewerNotFound(ReviewerNotFound result);
			T mapStudentNotFound(StudentNotFound result);
			T mapUserNotFound(UserNotFound result);
		}
	}
}
