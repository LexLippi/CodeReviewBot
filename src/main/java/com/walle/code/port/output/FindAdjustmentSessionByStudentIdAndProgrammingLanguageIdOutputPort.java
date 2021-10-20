package com.walle.code.port.output;

import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.domain.id.StudentId;
import com.walle.code.dto.row.SessionRow;

import java.util.Optional;

/**
 * Компонент для поиска незавершенных сеансов код-ревью по идентификаторам ученика и языка программирования
 * в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindAdjustmentSessionByStudentIdAndProgrammingLanguageIdOutputPort {
	/**
	 * Метод поиска незавершенных сеансов код-ревью по идентификаторам ученика и языка программирования
	 * в источнике данных.
	 * @param studentId идентификатор ученика
	 * @param programmingLanguageId идентификатор языка программирования
	 * @return {@link Optional}, содержащий {@link SessionRow} если такой есть, иначе пустой.
	 */
	Optional<SessionRow> findAdjustmentSessionByStudentIdAndProgrammingLanguageId(
			StudentId studentId, ProgrammingLanguageId programmingLanguageId);
}
