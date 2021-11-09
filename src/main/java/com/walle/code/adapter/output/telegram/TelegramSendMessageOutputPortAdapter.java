package com.walle.code.adapter.output.telegram;

import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.SendMessageOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Реализация {@link SendMessageOutputPort} с использованием Telegram API.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
@Slf4j
public class TelegramSendMessageOutputPortAdapter implements SendMessageOutputPort {
	@NonNull
	private final AbsSender absSender;

	@Override
	public void sendMessage(UserRow user, String text) {
		if (user.getChatId() == null) {
			return;
		}
		var message = new SendMessage();
		message.setChatId(user.getChatId().toString());
		message.setText(text);
		try {
			this.absSender.execute(message);
		} catch (TelegramApiException e) {
			log.error(e.getMessage(), e);
		}
	}
}
