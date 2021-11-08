package com.walle.code.port.input;

import com.walle.code.command.AddReviewerProgrammingLanguage;

/**
 * Вариант использования для отправки запроса на добавление языка программирования для ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface AddReviewerProgrammingLanguageUseCase {
	/**
	 * Метод отправки запроса на добавление языка программирования для ревьюера.
	 *
	 * @param command класс-команда с информацией для отправки запроса на добавление языка программирования
	 *                для ревьюера.
	 * @return результат выполнения операции.
	 */
	AddReviewerProgrammingLanguage.Result addReviewerProgrammingLanguage(AddReviewerProgrammingLanguage command);
}
