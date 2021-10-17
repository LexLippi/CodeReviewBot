package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.domain.id.StudentId;
import com.walle.code.dto.row.SessionRow;
import com.walle.code.dto.status.SessionStatus;
import com.walle.code.port.output.FindAdjustmentSessionByStudentIdAndProgrammingLanguageIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Optional;

/**
 * Реализация {@link FindAdjustmentSessionByStudentIdAndProgrammingLanguageIdOutputPort}
 * с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindAdjustmentSessionByStudentIdAndProgrammingLanguageIdOutputPortAdapter
		implements FindAdjustmentSessionByStudentIdAndProgrammingLanguageIdOutputPort {
	public static final String QUERY = "select id, id_reviewer, id_student, id_programming_language, c_status " +
			"from t_session where id_student = :studentId and id_programming_language = :programmingLanguageId " +
			"and c_status = :status";
	public static final String PARAM_STUDENT_ID = "studentId";
	public static final String PARAM_PROGRAMMING_LANGUAGE_ID = "programmingLanguageId";
	public static final String PARAM_STATUS = "status";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<SessionRow> rowMapper;

	@Override
	@NonNull
	public Optional<SessionRow> findAdjustmentSessionByStudentIdAndProgrammingLanguageId(
			@NonNull StudentId studentId, @NonNull ProgrammingLanguageId programmingLanguageId) {
		return this.entityManager.createQuery(QUERY, Tuple.class)
				.setParameter(PARAM_STUDENT_ID, studentId.getValue())
				.setParameter(PARAM_PROGRAMMING_LANGUAGE_ID, programmingLanguageId.getValue())
				.setParameter(PARAM_STATUS, SessionStatus.ADJUSTMENT.getTag())
				.getResultList()
				.stream()
				.findFirst()
				.map(this.rowMapper::mapRow);
	}
}
