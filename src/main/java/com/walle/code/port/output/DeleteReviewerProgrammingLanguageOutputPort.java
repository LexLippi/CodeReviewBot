package com.walle.code.port.output;

import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.domain.id.ReviewerId;

/**
 * Компонент для удаления языка программирования ревьюера в источнике данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface DeleteReviewerProgrammingLanguageOutputPort {
	/**
	 * Метод удаления языка программирования ревьюера в источнике данных
	 *
	 * @param reviewerId            идентификатор ревьюера
	 * @param programmingLanguageId идентификатор языка программирования
	 */
	void deleteReviewerProgrammingLanguage(ReviewerId reviewerId, ProgrammingLanguageId programmingLanguageId);
}
