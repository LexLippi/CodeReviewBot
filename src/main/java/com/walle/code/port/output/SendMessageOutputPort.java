package com.walle.code.port.output;

import com.walle.code.dto.row.UserRow;

/**
 * Компонент для отправки текстового сообщения пользователю
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface SendMessageOutputPort {
	/**
	 * Метод отправки текстового сообщения пользователю
	 *
	 * @param user пользователь
	 * @param text текст сообщения
	 */
	void sendMessage(UserRow user, String text);
}
