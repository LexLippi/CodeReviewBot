package com.walle.code.port.output;

import com.walle.code.dto.row.AdminRow;

import java.util.List;

/**
 * Компонент для поиска администраторов в источнике данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindAdminsOutputPort {
	/**
	 * Метод поиска администраторов в источнике данных.
	 *
	 * @return Список {@link AdminRow}
	 */
	List<AdminRow> findAdmins();
}
