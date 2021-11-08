package com.walle.code.port.input;

import com.walle.code.command.DeleteProgrammingLanguage;

/**
 * Вариант использования для удаления языка программирования ревьюера
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface DeleteProgrammingLanguageUseCase {
	/**
	 * Метод удаления языка программирования ревьюера
	 *
	 * @param command класс-команда с информацией для удаления языка программирования ревьюера
	 * @return результат выполнения операции: успех или ошибка
	 */
	DeleteProgrammingLanguage.Result deleteProgrammingLanguage(DeleteProgrammingLanguage command);
}
