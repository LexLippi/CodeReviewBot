package com.walle.code.port.output;

import com.walle.code.dto.row.ProgrammingLanguageRow;

import java.util.Optional;

/**
 * Компонент для поиска языка программирования по сокращению его названия в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindProgrammingLanguageByAliasOutputPort {
	/**
	 * Метод поиска языка программирования по его названию в источнике данных.
	 *
	 * @param alias сокращение название языка программирования.
	 * @return {@link Optional}, содержащий {@link ProgrammingLanguageRow}, если язык с таким именем найден,
	 * иначе пустой.
	 */
	Optional<ProgrammingLanguageRow> findProgrammingLanguageByAlias(String alias);
}
