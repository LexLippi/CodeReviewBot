package com.walle.code.port.input;

import com.walle.code.command.ReviewCode;

/**
 * Вариант использования для сохранения результатов код-ревью
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface ReviewCodeUseCase {
	/**
	 * Метод сохранения результатов код-ревью
	 *
	 * @param command класс-команда с информацией о код-ревью
	 * @return        результат выполнения операции
	 */
	ReviewCode.Result reviewCode(ReviewCode command);
}
