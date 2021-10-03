package com.walle.code.dto.row;

import com.walle.code.domain.id.ProgrammingLanguageId;
import lombok.Value;

/**
 * Компонент, описывающий представления языка программирования в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class ProgrammingLanguageRow {
	/**
	 * Идентификатор
	 */
	private final ProgrammingLanguageId id;

	/**
	 * Название
	 */
	private final String name;
}
