package com.walle.code.port.output;

import com.walle.code.domain.id.ReviewerId;
import com.walle.code.domain.id.StudentId;
import com.walle.code.dto.row.SessionRow;

import java.util.Optional;

/**
 * Компонент для поиска сеанса для код-ревью по идентификатору ученика и ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindReviewSessionByStudentIdAndReviewerIdOutputPort {
	/**
	 * Метод поиска сеанса для код-ревью по идентификатору ученика и ревьюера.
	 *
	 * @param studentId  идентификатор ученика
	 * @param reviewerId идентификатор ревьюера
	 * @return           {@link Optional}, содержаший {@link SessionRow}, если найден сеанс по заданным параметрам,
	 *                   иначе пустой
	 */
	Optional<SessionRow> findReviewSessionByStudentIdAndReviewerId(StudentId studentId, ReviewerId reviewerId);
}
