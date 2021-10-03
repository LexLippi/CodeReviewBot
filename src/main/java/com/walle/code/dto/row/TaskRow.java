package com.walle.code.dto.row;

import com.walle.code.domain.id.SessionId;
import com.walle.code.domain.id.TaskId;
import com.walle.code.dto.status.TaskStatus;
import lombok.Value;

/**
 * Компонент, описывающий представление задачи в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class TaskRow {
	/**
	 * Идентификатор
	 */
	private final TaskId id;

	/**
	 * Идентификатор сеанса, к которому принадлежит данная задача
	 */
	private final SessionId sessionId;

	/**
	 * Код для ревью
	 */
	private final String text;

	/**
	 * Комментарии к коду, оставленные ревьюером
	 */
	private final String reviewText;

	/**
	 * Статус задачи
	 */
	private final TaskStatus status;
}
