package com.walle.code.dto.row;

import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.domain.id.ReviewerId;
import com.walle.code.domain.id.ReviewerProgrammingLanguageId;
import lombok.Value;

/**
 * Компонент, представляющий информацию об одном из языков программирования ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class ReviewerProgrammingLanguageRow {
	/**
	 * Идентификатор
	 */
	private final ReviewerProgrammingLanguageId id;

	/**
	 * Идентификатор ревьюера
	 */
	private final ReviewerId reviewerId;

	/**
	 * Идентификатор языка программирования
	 */
	private final ProgrammingLanguageId programmingLanguageId;
}
