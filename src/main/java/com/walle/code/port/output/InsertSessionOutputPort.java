package com.walle.code.port.output;

import com.walle.code.domain.id.SessionId;
import com.walle.code.dto.row.SessionRow;

/**
 * Компонент для вставки сеанса в источник данных.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface InsertSessionOutputPort {
	/**
	 * Метод вставки сеанса в источник данных.
	 *
	 * @param session информация о сеансе.
	 * @return идентификатор, вставленного сеанса.
	 */
	SessionId insertSession(SessionRow session);
}
