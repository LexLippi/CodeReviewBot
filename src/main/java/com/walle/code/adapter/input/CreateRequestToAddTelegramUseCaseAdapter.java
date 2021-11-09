package com.walle.code.adapter.input;

import com.walle.code.command.CreateRequestToAddTelegram;
import com.walle.code.port.input.CreateRequestToAddTelegramUseCase;
import com.walle.code.port.output.FindUserByDiscordIdOutputPort;
import com.walle.code.port.output.InsertTelegramRequestOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

/**
 * Реализация {@link CreateRequestToAddTelegramUseCase}
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class CreateRequestToAddTelegramUseCaseAdapter implements CreateRequestToAddTelegramUseCase {
	@NonNull
	private final FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@NonNull
	private final InsertTelegramRequestOutputPort insertTelegramRequestOutputPort;

	@Override
	public CreateRequestToAddTelegram.Result createRequestToAddTelegram(CreateRequestToAddTelegram command) {
		return this.findUserByDiscordIdOutputPort.findUserByDiscordId(command.getDiscordUserId())
				.map(user -> this.transactionOperations.execute(status -> {
					this.insertTelegramRequestOutputPort.insertTelegramRequest(user.getId(), command
							.getTelegramNickname());
					return CreateRequestToAddTelegram.Result.success();
				}))
				.orElse(CreateRequestToAddTelegram.Result.userNotFound());
	}
}
