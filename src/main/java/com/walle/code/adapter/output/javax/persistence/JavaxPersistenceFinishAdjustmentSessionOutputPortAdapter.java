package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.SessionId;
import com.walle.code.port.output.FinishAdjustmentSessionOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link FinishAdjustmentSessionOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFinishAdjustmentSessionOutputPortAdapter
		implements FinishAdjustmentSessionOutputPort {
	public static final String QUERY = "update t_session set c_status = 'f' where id = :id";
	public static final String PARAM_ID = "id";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public void finishAdjustmentSession(SessionId sessionId) {
		this.entityManager.createQuery(QUERY).setParameter(PARAM_ID, sessionId.getValue()).executeUpdate();
	}
}
