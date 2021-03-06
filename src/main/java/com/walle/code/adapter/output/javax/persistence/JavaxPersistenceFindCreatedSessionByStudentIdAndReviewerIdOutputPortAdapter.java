package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.domain.id.ReviewerId;
import com.walle.code.domain.id.StudentId;
import com.walle.code.dto.row.SessionRow;
import com.walle.code.dto.status.SessionStatus;
import com.walle.code.port.output.FindReviewSessionByStudentIdAndReviewerIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Optional;

/**
 * Реализация {@link FindReviewSessionByStudentIdAndReviewerIdOutputPort}
 * с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindCreatedSessionByStudentIdAndReviewerIdOutputPortAdapter
		implements FindReviewSessionByStudentIdAndReviewerIdOutputPort {
	public static final String QUERY = "select id, id_reviewer, id_student, id_programming_language, c_status " +
			"from t_session where id_student = :studentId and id_reviewer = :reviewerId " +
			"and c_status = :status";
	public static final String PARAM_STUDENT_ID = "studentId";
	public static final String PARAM_REVIEWER_ID = "reviewerId";
	public static final String PARAM_STATUS = "status";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<SessionRow> rowMapper;

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public Optional<SessionRow> findReviewSessionByStudentIdAndReviewerId(@NonNull StudentId studentId,
																		  @NonNull ReviewerId reviewerId) {
		return this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.setParameter(PARAM_STUDENT_ID, studentId.getValue())
				.setParameter(PARAM_REVIEWER_ID, reviewerId.getValue())
				.setParameter(PARAM_STATUS, SessionStatus.REVIEW.getTag())
				.getResultList()
				.stream()
				.findFirst()
				.map(result -> this.rowMapper.mapRow((Tuple) result));
	}
}
