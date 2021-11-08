package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

/**
 * Класс-команда с информацией для добавления языка программирования ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AddReviewerProgrammingLanguage extends Command {
	private final String nickname;

	private final String programmingLanguageAlias;

	public static AddReviewerProgrammingLanguage of(@NonNull DiscordUserId discordUserId, @NonNull String nickname,
													@NonNull String programmingLanguageAlias) {
		return of(Instant.now(), discordUserId, nickname, programmingLanguageAlias);
	}

	public static AddReviewerProgrammingLanguage of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
													@NonNull String nickname,
													@NonNull String programmingLanguageAlias) {
		return new AddReviewerProgrammingLanguage(timestamp, discordUserId, nickname, programmingLanguageAlias);
	}

	private AddReviewerProgrammingLanguage(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
										   @NonNull String nickname,
										   @NonNull String programmingLanguageAlias) {
		super(timestamp, discordUserId);
		this.nickname = nickname;
		this.programmingLanguageAlias = programmingLanguageAlias;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success() {
			return Success.INSTANCE;
		}

		static Result reviewerProgrammingLanguageAlreadyExists() {
			return ReviewerProgrammingLanguageAlreadyExists.INSTANCE;
		}

		static Result reviewerNotFound() {
			return ReviewerNotFound.INSTANCE;
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
		 * Этот язык программирования уже установлен для данного ревьюера
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		enum ReviewerProgrammingLanguageAlreadyExists implements Result {
			INSTANCE;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapReviewerProgrammingLanguageAlreadyExists(this);
			}
		}

		/**
		 * Ревьюер с этими данными не найден
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

		interface Mapper<T> {
			T mapSuccess(Success result);
			T mapReviewerProgrammingLanguageAlreadyExists(ReviewerProgrammingLanguageAlreadyExists result);
			T mapReviewerNotFound(ReviewerNotFound result);
		}
	}
}
