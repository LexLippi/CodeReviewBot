package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.StudentId;
import com.walle.code.dto.row.StudentRow;
import com.walle.code.port.output.InsertStudentOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link InsertStudentOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceInsertStudentOutputPortAdapter implements InsertStudentOutputPort {
	public static final String QUERY = "insert into t_student(id_user) values(:userId) returning id";

	public static final String PARAM_USER_ID = "userId";

	@NonNull
	private final EntityManager entityManager;

	@Override
	@NonNull
	public StudentId insertStudent(@NonNull StudentRow student) {
		return StudentId.of((Integer) this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_USER_ID, student.getUserId().getValue())
				.getSingleResult());
	}
}
