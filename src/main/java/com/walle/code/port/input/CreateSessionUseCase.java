package com.walle.code.port.input;

import com.walle.code.command.CreateSession;

/**
 * Вариант использования для создания сеанса проверки кода.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface CreateSessionUseCase {
	/**
	 * Метод создания сеанса проверки кода
	 *
	 * @param command класс-команда с данными для создания сеанса проверки кода.
	 * @return результат выполнения операции: успех или ошибка.
	 */
	CreateSession.Result createSessionUseCase(CreateSession command);
}
