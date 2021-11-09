package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.domain.id.TaskId;
import lombok.*;

import java.time.Instant;

/**
 * Класс-команда с информацией для создания сеанса код-ревью.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateSession extends Command {

	private final String codeText;

	private final String taskUrlLink;

	private final String programmingLanguageName;


	public static CreateSession of(@NonNull DiscordUserId discordUserId, @NonNull String codeText,
								   @NonNull String programmingLanguageName, @NonNull String taskUrlLink) {
		return of(Instant.now(), discordUserId, codeText, programmingLanguageName, taskUrlLink);
	}

	public static CreateSession of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
								   @NonNull String codeText, @NonNull String programmingLanguageName,
								   @NonNull String taskUrlLink) {
		return new CreateSession(timestamp, discordUserId, codeText, programmingLanguageName, taskUrlLink);
	}

	private CreateSession(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId, @NonNull String codeText,
						 @NonNull String programmingLanguageName, @NonNull String taskUrlLink) {
		super(timestamp, discordUserId);
		this.codeText = codeText;
		this.programmingLanguageName = programmingLanguageName;
		this.taskUrlLink = taskUrlLink;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success(TaskId taskId) {
			return Success.of(taskId);
		}

		static Result reviewerNotFound() {
			return ReviewerNotFound.INSTANCE;
		}

		static Result programmingLanguageNotFound() {
			return ProgrammingLanguageNotFound.INSTANCE;
		}

		static Result studentNotFound() {
			return StudentNotFound.INSTANCE;
		}

		static Result userNotFound() {
			return UserNotFound.INSTANCE;
		}

		/**
		 * Сеанс код-ревью создан успешно.
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		@Value(staticConstructor = "of")
		class Success implements Result {
			private final TaskId taskId;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapSuccess(this);
			}
		}

		/**
		 * Ревьюер не найден.
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
		 * Язык программирования по заданному алиасу не найден.
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum ProgrammingLanguageNotFound implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapProgrammingLanguageNotFound(this);
			}
		}

		/**
		 * Ученик не найден.
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
		 * Пользователь с таким идентификатором не найден.
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
			T mapReviewerNotFound(ReviewerNotFound result);
			T mapProgrammingLanguageNotFound(ProgrammingLanguageNotFound result);
			T mapStudentNotFound(StudentNotFound result);
			T mapUserNotFound(UserNotFound result);
		}
	}
}
