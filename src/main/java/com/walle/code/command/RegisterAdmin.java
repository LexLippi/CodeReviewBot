package com.walle.code.command;

import com.walle.code.domain.id.AdminId;
import com.walle.code.domain.id.DiscordUserId;
import lombok.*;

import java.time.Instant;

/**
 * Класс-команда с информацией для регистрации админа.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RegisterAdmin extends Command {
	private final String nickname;

	public static RegisterAdmin of(@NonNull DiscordUserId discordUserId, @NonNull String nickname) {
		return of(Instant.now(), discordUserId, nickname);
	}

	public static RegisterAdmin of(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
								   @NonNull String nickname) {
		return new RegisterAdmin(timestamp, discordUserId, nickname);
	}

	private RegisterAdmin(@NonNull Instant timestamp, @NonNull DiscordUserId discordUserId,
						  @NonNull String nickname) {
		super(timestamp, discordUserId);
		this.nickname = nickname;
	}

	public interface Result {
		<T> T mapWith(Mapper<T> mapper);

		static Result success() {
			return Success.INSTANCE;
		}

		static Result adminAlreadyRegister(AdminId adminId) {
			return AdminAlreadyRegister.of(adminId);
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
		 * Админ уже зарегистрирован
		 *
		 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
		 * @since 21.1.0
		 */
		@Value(staticConstructor = "of")
		class AdminAlreadyRegister implements Result {
			private final AdminId adminId;

			@Override
			public <T> T mapWith(Mapper<T> mapper) {
				return mapper.mapAdminAlreadyRegister(this);
			}
		}

		interface Mapper<T> {
			T mapSuccess(Success result);
			T mapAdminAlreadyRegister(AdminAlreadyRegister result);
		}
	}
}
