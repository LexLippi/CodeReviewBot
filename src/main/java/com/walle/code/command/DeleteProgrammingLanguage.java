package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

/**
 * Класс-команда с информацией для удаления языка программирования ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class DeleteProgrammingLanguage extends Command {
	private final String programmingLanguageAlias;

	public static DeleteProgrammingLanguage of(@NonNull DiscordUserId discordUserId,
										@NonNull String programmingLanguageAlias) {
		return of(Instant.now(), discordUserId, programmingLanguageAlias);
	}

	public static DeleteProgrammingLanguage of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
										@NonNull String programmingLanguageAlias) {
		return new DeleteProgrammingLanguage(timestamp, discordUserId, programmingLanguageAlias);
	}

	public DeleteProgrammingLanguage(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
									 @NonNull String programmingLanguageAlias) {
		super(timestamp, discordUserId);
		this.programmingLanguageAlias = programmingLanguageAlias;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success() {
			return Success.INSTANCE;
		}

		static Result reviewerNotFound() {
			return ReviewerNotFound.INSTANCE;
		}

		static Result programmingLanguageNotFound() {
			return ProgrammingLanguageNotFound.INSTANCE;
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
		 * Язык программирования не найден
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

		interface Mapper<T> {
			T mapSuccess(Success result);
			T mapReviewerNotFound(ReviewerNotFound result);
			T mapProgrammingLanguageNotFound(ProgrammingLanguageNotFound result);
		}
	}
}
