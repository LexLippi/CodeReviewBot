package com.walle.code.port.output;

import com.walle.code.domain.id.StudentId;
import com.walle.code.dto.row.StudentRow;

/**
 * Компонент для вставки ученика в источник данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface InsertStudentOutputPort {
	/**
	 * Метод вставки ученика в источник данных
	 *
	 * @param student информация об ученике, которую необходимо вставить в источник данных
	 * @return        идентификатор ученика, вставленного в источник данных
	 */
	StudentId insertStudent(StudentRow student);
}
