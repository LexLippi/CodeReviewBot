package com.walle.code.port.input;

import com.walle.code.command.ApproveAdmin;

/**
 * Вариант использования для подтверждения регистрации админа
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface ApproveAdminUseCase {
	/**
	 * Метод подтверждения регистрации админа
	 *
	 * @param command класс-команда с информацией для подтверждения регистрации админа
	 * @return Результат выполнения операции подтверждения админа
	 */
	ApproveAdmin.Result approveAdmin(ApproveAdmin command);
}
