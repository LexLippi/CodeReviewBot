package com.walle.code.port.output;

import com.walle.code.domain.id.TaskId;
import com.walle.code.dto.row.TaskRow;

/**
 * Компонент для вставки задачи в источник данных.
 *
 *
 */
@FunctionalInterface
public interface InsertTaskOutputPort {
	/**
	 * Метод вставки задачи в источник данных.
	 *
	 * @param task информация о задаче.
	 * @return Идентификатор, вставленной задачи.
	 */
	TaskId insertTask(TaskRow task);
}
