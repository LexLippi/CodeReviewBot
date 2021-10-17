package com.walle.code.port.input;

import com.walle.code.command.RegisterReviewer;

/**
 * Вариант использования для регистрации ревьюера
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface RegisterReviewerUseCase {
	/**
	 * Метод регистрации ревьюера
	 *
	 * @param command класс-команда с информацией для регистрации ревьюера
	 * @return результат регистрации ревьюера: успех или ошибка, поскольку ревьюер уже зарегистрирован
	 */
	RegisterReviewer.Result registerReviewer(RegisterReviewer command);
}
