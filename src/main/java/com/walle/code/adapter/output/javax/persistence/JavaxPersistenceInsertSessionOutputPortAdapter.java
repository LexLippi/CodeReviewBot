package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.SessionId;
import com.walle.code.dto.row.SessionRow;
import com.walle.code.port.output.InsertSessionOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link InsertSessionOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceInsertSessionOutputPortAdapter implements InsertSessionOutputPort {
	public static final String QUERY =
			"insert into t_session(id_reviewer, id_student, id_programming_language, c_status) " +
					"values(:reviewerId, :studentId, :programmingLanguageId, :status) " +
					"returning id";

	public static final String PARAM_REVIEWER_ID = "reviewerId";
	public static final String PARAM_STUDENT_ID = "studentId";
	public static final String PARAM_PROGRAMMING_LANGUAGE_ID = "programmingLanguageId";
	public static final String PARAM_STATUS = "status";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public SessionId insertSession(SessionRow session) {

		return SessionId.of((Integer) this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_REVIEWER_ID, session.getReviewerId().getValue())
				.setParameter(PARAM_STUDENT_ID, session.getStudentId().getValue())
				.setParameter(PARAM_PROGRAMMING_LANGUAGE_ID, session.getProgrammingLanguageId().getValue())
				.setParameter(PARAM_STATUS, session.getStatus().getTag())
				.getSingleResult());
	}
}
