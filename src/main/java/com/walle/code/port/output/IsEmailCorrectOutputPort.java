package com.walle.code.port.output;

/**
 * Компонент для проверки того, что адрес электронной почты является корректным
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface IsEmailCorrectOutputPort {
	/**
	 * Метод проверки того, что адрес электронной почты является корректным
	 *
	 * @param email адрес электронной почты
	 * @return является ли указанный адрес электронной почты корректным или нет
	 */
	boolean isEmailCorrect(String email);
}
