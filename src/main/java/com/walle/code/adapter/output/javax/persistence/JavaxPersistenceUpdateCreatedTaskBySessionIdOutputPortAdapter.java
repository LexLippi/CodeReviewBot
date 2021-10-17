package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.SessionId;
import com.walle.code.dto.status.TaskStatus;
import com.walle.code.port.output.UpdateCreatedTaskBySessionIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link UpdateCreatedTaskBySessionIdOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceUpdateCreatedTaskBySessionIdOutputPortAdapter
		implements UpdateCreatedTaskBySessionIdOutputPort {
	public static final String QUERY = "update t_task set c_status = :status, c_review_text = :text " +
			"where id_session = :sessionId and c_status = 'c'";
	public static final String PARAM_SESSION_ID = "sessionId";
	public static final String PARAM_STATUS = "status";
	public static final String PARAM_TEXT = "text";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public void updateAdjustmentTaskBySessionId(SessionId sessionId, String reviewText, TaskStatus taskStatus) {
		this.entityManager.createQuery(QUERY)
				.setParameter(PARAM_SESSION_ID, sessionId.getValue())
				.setParameter(PARAM_STATUS, taskStatus.getTag())
				.setParameter(PARAM_TEXT, reviewText)
				.executeUpdate();
	}
}
