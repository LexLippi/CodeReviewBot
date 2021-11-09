package com.walle.code.listener;

import com.walle.code.handler.telegram.AddTelegramChatIdToUserHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Реализация логики Telegram-бота
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Slf4j
public final class TelegramNotificationBot extends TelegramLongPollingCommandBot {
	public static final String NON_COMMAND_RESULT = "Извините, я не понимаю, что вы от меня хотите";

	@NonNull
	private final String botName;

	@NonNull
	private final String botToken;

	public TelegramNotificationBot(@NonNull String botName, @NonNull String botToken,
								   @NonNull AddTelegramChatIdToUserHandler addTelegramChatIdToUserHandler) {
		super();

		this.botName = botName;
		this.botToken = botToken;

		register(addTelegramChatIdToUserHandler);
	}

	@Override
	public String getBotUsername() {
		return this.botName;
	}

	@Override
	public void processNonCommandUpdate(Update update) {
		var message = new SendMessage();
		message.setText(NON_COMMAND_RESULT);
		message.setChatId(update.getMessage().getChatId().toString());
		try {
			execute(message);
		} catch (TelegramApiException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public String getBotToken() {
		return this.botToken;
	}
}
