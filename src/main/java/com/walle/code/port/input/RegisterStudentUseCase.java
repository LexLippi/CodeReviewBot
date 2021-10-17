package com.walle.code.port.input;

import com.walle.code.command.RegisterStudent;

/**
 * Вариант использования для регистрации ученика
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface RegisterStudentUseCase {
	/**
	 * Метод регистрации ученика
	 *
	 * @param command класс-команда с информацией для регистрации ученика
	 * @return результат регистрации ученика: успех или ошибка, поскольку пользователь уже зарегистрирован
	 */
	RegisterStudent.Result registerStudent(RegisterStudent command);
}
