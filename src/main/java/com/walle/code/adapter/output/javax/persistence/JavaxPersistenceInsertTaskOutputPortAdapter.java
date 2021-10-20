package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.TaskId;
import com.walle.code.dto.row.TaskRow;
import com.walle.code.port.output.InsertTaskOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link InsertTaskOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceInsertTaskOutputPortAdapter implements InsertTaskOutputPort {
	public static final String QUERY = "insert into t_task(c_text, c_status, id_session) " +
			"values(:text, :status, :sessionId) " +
			"returning id";

	public static final String PARAM_TEXT = "text";
	public static final String PARAM_SESSION_ID = "sessionId";
	public static final String PARAM_STATUS = "status";

	@NonNull
	private final EntityManager entityManager;

	@Override
	@NonNull
	public TaskId insertTask(@NonNull TaskRow task) {
		return TaskId.of((Integer) this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_TEXT, task.getText())
				.setParameter(PARAM_SESSION_ID, task.getSessionId().getValue())
				.setParameter(PARAM_STATUS, task.getStatus().getTag())
				.getSingleResult());
	}
}
