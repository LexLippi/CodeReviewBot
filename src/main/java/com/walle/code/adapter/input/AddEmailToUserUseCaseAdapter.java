package com.walle.code.adapter.input;

import com.walle.code.command.AddEmailToUser;
import com.walle.code.port.input.AddEmailToUserUseCase;
import com.walle.code.port.output.IsEmailCorrectOutputPort;
import com.walle.code.port.output.UpdateUserEmailByDiscordIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Реализация {@link AddEmailToUserUseCase}
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class AddEmailToUserUseCaseAdapter implements AddEmailToUserUseCase {
	@NonNull
	private final IsEmailCorrectOutputPort isEmailCorrectOutputPort;

	@NonNull
	private final UpdateUserEmailByDiscordIdOutputPort updateUserEmailByDiscordIdOutputPort;

	@Override
	@NonNull
	public AddEmailToUser.Result addEmailToUser(@NonNull AddEmailToUser command) {
		if (isEmailCorrectOutputPort.isEmailCorrect(command.getEmail())) {
			return AddEmailToUser.Result.emailIsNotCorrect();
		}
		this.updateUserEmailByDiscordIdOutputPort.updateUserEmailByDiscordId(command.getEmail(),
				command.getDiscordUserId());
		return AddEmailToUser.Result.success();
	}
}
