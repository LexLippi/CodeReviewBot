package com.walle.code.port.input;

import com.walle.code.command.AddTelegramChatIdToUser;

/**
 * Вариант использования для добавления чата в Telegram для связи с пользователем.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface AddTelegramChatIdToUserUseCase {
	/**
	 * Метод добавления чата в Telegram для связи с пользователем.
	 *
	 * @param command класс-команда с информацией для добавления чата в Telegram для связи с пользователем.
	 * @return результат выполнения операции: успех или ошибка
	 */
	AddTelegramChatIdToUser.Result addTelegramChatIdToUser(AddTelegramChatIdToUser command);
}
