package com.walle.code.adapter.input;

import com.walle.code.command.AddTelegramChatIdToUser;
import com.walle.code.port.input.AddTelegramChatIdToUserUseCase;
import com.walle.code.port.output.FindUserIdByTelegramNicknameOutputPort;
import com.walle.code.port.output.UpdateUserChatIdByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Реализация {@link AddTelegramChatIdToUserUseCase}
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class AddTelegramChatIdToUserUseCaseAdapter implements AddTelegramChatIdToUserUseCase {
	@NonNull
	private final FindUserIdByTelegramNicknameOutputPort findUserIdByTelegramNicknameOutputPort;

	@NonNull
	private final UpdateUserChatIdByIdOutputPort updateUserChatIdByIdOutputPort;

	@Override
	@NonNull
	public AddTelegramChatIdToUser.Result addTelegramChatIdToUser(@NonNull AddTelegramChatIdToUser command) {
		return this.findUserIdByTelegramNicknameOutputPort.findUserIdByTelegramNicknameOutputPort(
				command.getTelegramNickname())
				.map(userId -> {
					this.updateUserChatIdByIdOutputPort.updateUserChatIdById(userId, command.getTelegramChatId());
					return AddTelegramChatIdToUser.Result.success();
				})
				.orElse(AddTelegramChatIdToUser.Result.requestForConnectNotFound());
	}
}
