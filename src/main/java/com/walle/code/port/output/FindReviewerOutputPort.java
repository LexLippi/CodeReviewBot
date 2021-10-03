package com.walle.code.port.output;

import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.dto.row.ReviewerRow;

import java.util.Optional;

/**
 * Компонент для поиска ревьюера, который может проверять задачи на указанном языке программирования.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindReviewerOutputPort {
	/**
	 * Метод поиска ревьюера, который может проверять задачи на указанном языке программирования.
	 *
	 * @param programmingLanguageId идентификатор заданного языка программирования
	 * @return {@link Optional} содержащий {@link ReviewerRow}, если по заданным параметрам он найден, иначе пустой.
	 */
	Optional<ReviewerRow> findReviewer(ProgrammingLanguageId programmingLanguageId);
}
