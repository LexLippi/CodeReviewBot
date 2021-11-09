package com.walle.code.port.input;

import com.walle.code.command.CreateRequestToAddTelegram;

/**
 * Вариант использования для создания запроса на добавления Telegram в качестве источника уведомлений
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface CreateRequestToAddTelegramUseCase {
	/**
	 * Метод создания запроса на добавления Telegram в качестве источника уведомлений
	 *
	 * @param command класс-команда с информацией для создания запроса на добавления Telegram в качестве
	 *                источника уведомлений
	 * @return результат выполнения операции: успех или ошибка
	 */
	CreateRequestToAddTelegram.Result createRequestToAddTelegram(CreateRequestToAddTelegram command);
}
