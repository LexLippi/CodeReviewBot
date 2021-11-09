package com.walle.code.handler.discord;

import com.walle.code.command.CreateRequestToAddTelegram;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.CreateRequestToAddTelegramUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * Компонент для создания запроса на добавления Telegram в качестве источника уведомлений
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class CreateRequestToAddTelegramHandler {
	public static final String SPACE = " ";
	public static final int SKIP_COUNT = 1;

	@NonNull
	private final CreateRequestToAddTelegramUseCase createRequestToAddTelegramUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		return this.createRequestToAddTelegramUseCase.createRequestToAddTelegram(CreateRequestToAddTelegram.of(
				DiscordUserId.of(event.getAuthor().getId()),
				Arrays.stream(event.getMessage().getContentRaw().split(SPACE))
						.skip(SKIP_COUNT)
						.collect(joining(SPACE))))
				.mapWith(CreateRequestToAddTelegramResultToStringMapper.INSTANCE);
	}

	private enum CreateRequestToAddTelegramResultToStringMapper
			implements CreateRequestToAddTelegram.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "You request successful registered. Now, please send message " +
				"into our telegram bot";
		public static final String USER_NOT_FOUND_MESSAGE = "Sorry, user with you id not found. " +
				"Please register at our bot. Send !register";

		@Override
		public String mapSuccess(CreateRequestToAddTelegram.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapUserNotFound(CreateRequestToAddTelegram.Result.UserNotFound result) {
			return USER_NOT_FOUND_MESSAGE;
		}
	}
}
