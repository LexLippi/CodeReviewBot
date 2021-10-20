package com.walle.code.command;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.domain.id.StudentId;
import lombok.*;

import java.time.Instant;

/**
 * Класс-команда с информацией для регистрации ученика.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RegisterStudent extends Command {
	private final String nickname;

	public static RegisterStudent of(@NonNull DiscordUserId discordUserId, @NonNull String nickname) {
		return of(Instant.now(), discordUserId, nickname);
	}

	public static RegisterStudent of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
									 @NonNull String nickname) {
		return new RegisterStudent(timestamp, discordUserId, nickname);
	}

	private RegisterStudent(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
							@NonNull String nickname) {
		super(timestamp, discordUserId);
		this.nickname = nickname;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success(StudentId studentId) {
			return Success.of(studentId);
		}

		static Result studentAlreadyRegister(StudentId studentId) {
			return StudentAlreadyRegister.of(studentId);
		}

		/**
		 * Ученик успешно зарегистрирован
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		@Value(staticConstructor = "of")
		class Success implements Result {
			private final StudentId studentId;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapSuccess(this);
			}
		}

		/**
		 * Ученик уже зарегистрирован
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		@Value(staticConstructor = "of")
		class StudentAlreadyRegister implements Result {
			private final StudentId studentId;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapStudentAlreadyRegister(this);
			}
		}

		interface Mapper<T> {
			T mapSuccess(Success result);
			T mapStudentAlreadyRegister(StudentAlreadyRegister result);
		}
	}
}
