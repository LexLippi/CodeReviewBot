package com.walle.code.port.output;

import com.walle.code.domain.id.SessionId;
import com.walle.code.dto.status.TaskStatus;

/**
 * Компонент для обновления открытой задачи по идентификатору сеанса.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface UpdateCreatedTaskBySessionIdOutputPort {
	/**
	 * Метод обновления открытой задачи по идентификатору сеанса.
	 *
	 * @param sessionId  идентификатор сеанса
	 * @param reviewText текст код-ревью
	 * @param taskStatus статус задачи
	 */
	void updateAdjustmentTaskBySessionId(SessionId sessionId, String reviewText, TaskStatus taskStatus);
}
