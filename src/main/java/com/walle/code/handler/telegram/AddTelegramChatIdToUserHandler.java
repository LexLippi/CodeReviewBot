package com.walle.code.handler.telegram;

import com.walle.code.command.AddTelegramChatIdToUser;
import com.walle.code.port.input.AddTelegramChatIdToUserUseCase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Компонент для добавления идентификатора чата в Telegram к пользователю.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Slf4j
public final class AddTelegramChatIdToUserHandler extends BotCommand {
	private final AddTelegramChatIdToUserUseCase addTelegramChatIdToUserUseCase;

	public AddTelegramChatIdToUserHandler(@NonNull String identifier, @NonNull String description,
										  @NonNull AddTelegramChatIdToUserUseCase addTelegramChatIdToUserUseCase) {
		super(identifier, description);
		this.addTelegramChatIdToUserUseCase = addTelegramChatIdToUserUseCase;
	}

	@Override
	public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
		var message = new SendMessage();
		message.setChatId(chat.getId().toString());
		message.setText(this.addTelegramChatIdToUserUseCase.addTelegramChatIdToUser(AddTelegramChatIdToUser.of(
				user.getUserName(),
				chat.getId()))
				.mapWith(AddTelegramChatIdToUserResultToStringMapper.INSTANCE));
		try {
			absSender.execute(message);
		} catch (TelegramApiException e) {
			log.error(e.getMessage(), e);
		}
	}

	private enum AddTelegramChatIdToUserResultToStringMapper implements AddTelegramChatIdToUser.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "Вы успешно привязали ваш аккаунт в Telegram к аккаунту в нашем " +
				"боте в Discord. Теперь сюда вам будут приходить различные уведомления";
		public static final String REQUEST_FOR_CONNECT_NOT_FOUND_MESSAGE = "По вашему никнейму в Telegram не найдено " +
				"ни одного запроса на подключение аккаунта в нашем боте в Discord";


		@Override
		public String mapSuccess(AddTelegramChatIdToUser.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapRequestForConnectNotFound(AddTelegramChatIdToUser.Result.RequestForConnectNotFound result) {
			return REQUEST_FOR_CONNECT_NOT_FOUND_MESSAGE;
		}
	}
}
