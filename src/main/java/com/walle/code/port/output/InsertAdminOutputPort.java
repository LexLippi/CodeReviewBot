package com.walle.code.port.output;

import com.walle.code.dto.row.AdminRow;

/**
 * Компонент для вставки админа в источник данных
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface InsertAdminOutputPort {
	/**
	 * Метод вставки админа в источник данных
	 *
	 * @param admin информация об админе
	 */
	void insertAdmin(AdminRow admin);
}
