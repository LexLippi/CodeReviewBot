package com.walle.code.port.output;

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
public interface FindAdjustmentSessionByURLLinkOutputPort {
	/**
	 * Метод поиска незавершенных сеансов код-ревью по идентификаторам ученика и языка программирования
	 * в источнике данных.
	 * @param urlLink ссылка на задачу, код которой был прислан
	 * @return {@link Optional}, содержащий {@link SessionRow} если такой есть, иначе пустой.
	 */
	Optional<SessionRow> findAdjustmentSessionByURLLink(String urlLink);
}
