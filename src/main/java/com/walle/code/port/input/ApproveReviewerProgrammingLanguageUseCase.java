package com.walle.code.port.input;

import com.walle.code.command.ApproveReviewerProgrammingLanguage;

/**
 * Вариант использования для подтверждения добавления языка программирования ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface ApproveReviewerProgrammingLanguageUseCase {
	/**
	 * Метод подтверждения добавления языка программирования ревьюера.
	 *
	 * @param command класс-команда с информацией для подтверждения добавления языка программирования ревьюера.
	 * @return результат выполнения операции: успех или ошибка
	 */
	ApproveReviewerProgrammingLanguage.Result approveReviewerProgrammingLanguage(ApproveReviewerProgrammingLanguage
																						 command);
}
