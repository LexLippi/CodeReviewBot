package com.walle.code.port.input;

import com.walle.code.command.AddEmailToUser;

/**
 * Вариант использования для добавления электронной почты к пользователю.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface AddEmailToUserUseCase {
	/**
	 * Метод добавления электронной почты к пользователю.
	 *
	 * @param command класс-команда с информацией для добавления электронной почты к пользователю
	 * @return результат выполнения операции: успех или ошибка
	 */
	AddEmailToUser.Result addEmailToUser(AddEmailToUser command);
}
