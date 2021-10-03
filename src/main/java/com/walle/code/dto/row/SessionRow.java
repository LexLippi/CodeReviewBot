package com.walle.code.dto.row;

import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.domain.id.ReviewerId;
import com.walle.code.domain.id.SessionId;
import com.walle.code.domain.id.StudentId;
import com.walle.code.dto.status.SessionStatus;
import lombok.Value;

/**
 * Компонент, описывающий представление сеанса проверки кода в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class SessionRow {
	/**
	 * Идентификатор
	 */
	private final SessionId id;

	/**
	 * Идентификатор ревьюера
	 */
	private final ReviewerId reviewerId;

	/**
	 * Идентификатор студента
	 */
	private final StudentId studentId;

	/**
	 * Идентификатор языка программирования
	 */
	private final ProgrammingLanguageId programmingLanguageId;

	/**
	 * Статус
	 */
	private final SessionStatus status;


}
