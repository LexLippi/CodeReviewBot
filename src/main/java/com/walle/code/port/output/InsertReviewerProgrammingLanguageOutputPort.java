package com.walle.code.port.output;

import com.walle.code.domain.id.ReviewerProgrammingLanguageId;
import com.walle.code.dto.row.ReviewerProgrammingLanguageRow;

/**
 * Компонент для вставки языка программирования ревьюера в источник данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface InsertReviewerProgrammingLanguageOutputPort {
	/**
	 * Метод вставки языка программирования ревьюера в источник данных.
	 *
	 * @param reviewerProgrammingLanguage информация о языке программирования ревьюера
	 * @return {@link ReviewerProgrammingLanguageId} идентификатор, вставленной записи
	 */
	ReviewerProgrammingLanguageId insertReviewerProgrammingLanguage(ReviewerProgrammingLanguageRow
																			reviewerProgrammingLanguage);
}
