package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.StudentRow;
import com.walle.code.port.output.FindStudentByUserIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Реализация {@link FindStudentByUserIdOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindStudentByUserIdOutputPortAdapter implements FindStudentByUserIdOutputPort {
	public static final String QUERY = "select * from v_student where id_user = :userId";
	public static final String PARAM_USER_ID = "userId";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public Optional<StudentRow> findStudentByUserId(UserId userId) {
		return this.entityManager.createNamedQuery(QUERY, StudentRow.class)
				.setParameter(PARAM_USER_ID, userId.getValue())
				.getResultList()
				.stream()
				.findFirst();
	}
}
