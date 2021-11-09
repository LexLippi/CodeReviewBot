package com.walle.code.port.input;

import com.walle.code.command.RegisterAdmin;

/**
 * Вариант использования для регистрации админа
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface RegisterAdminUseCase {
	/**
	 * Метод регистрации админа
	 *
	 * @param command класс-команда с информацией для регистрации админа
	 * @return результат регистрации админа: успех или ошибка, поскольку админ уже зарегистрирован
	 */
	RegisterAdmin.Result registerAdmin(RegisterAdmin command);
}
