package com.walle.code.port.output;

import com.walle.code.domain.id.SessionId;

/**
 * Компонент для обновления статуса выполняемого сеанса
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FinishAdjustmentSessionOutputPort {
	/**
	 * Метод обновления статуса выполняемого сеанса
	 *
	 * @param sessionId идентификатор сеанса
	 */
	void finishAdjustmentSession(SessionId sessionId);
}
