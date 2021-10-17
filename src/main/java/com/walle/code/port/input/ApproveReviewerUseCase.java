package com.walle.code.port.input;

import com.walle.code.command.ApproveReviewer;

/**
 * Вариант использования для подтверждения регистрации ревьюера
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface ApproveReviewerUseCase {
	/**
	 * Метод подтверждения регистрации ревьюера
	 *
	 * @param command класс-команда с информацией для подтверждения регистрации ревьюера
	 * @return Результат выполнения операции подтверждения ревьюера
	 */
	ApproveReviewer.Result approveReviewer(ApproveReviewer command);
}
