package com.walle.code.domain.id;

import lombok.Value;

/**
 * Компонент, описывающий идентификатор задачи.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class TaskId {
	/**
	 * Значение
 	 */
	private final int value;
}
