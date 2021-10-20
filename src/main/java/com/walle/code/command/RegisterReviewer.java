package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.domain.id.ReviewerId;
import lombok.*;

import java.time.Instant;

/**
 * Класс-команда с информацией для регистрации ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RegisterReviewer extends Command {
	private final String nickname;

	public static RegisterReviewer of(@NonNull DiscordUserId discordUserId, @NonNull String nickname) {
		return of(Instant.now(), discordUserId, nickname);
	}

	public static RegisterReviewer of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
									  @NonNull String nickname) {
		return new RegisterReviewer(timestamp, discordUserId, nickname);
	}

	private RegisterReviewer(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
							 @NonNull String nickname) {
		super(timestamp, discordUserId);
		this.nickname = nickname;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success() {
			return Success.INSTANCE;
		}

		static Result reviewerAlreadyRegister(ReviewerId reviewerId) {
			return ReviewerAlreadyRegister.of(reviewerId);
		}

		/**
		 * Заявка на регистрацию ревьюера успешно отправлена
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
		 * Ревьюер уже зарегистрирован
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		@Value(staticConstructor = "of")
		class ReviewerAlreadyRegister implements Result {
			private final ReviewerId reviewerId;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapReviewerAlreadyRegister(this);
			}
		}

		interface Mapper<T> {
			T mapSuccess(Success result);
			T mapReviewerAlreadyRegister(ReviewerAlreadyRegister result);
		}
	}
}
