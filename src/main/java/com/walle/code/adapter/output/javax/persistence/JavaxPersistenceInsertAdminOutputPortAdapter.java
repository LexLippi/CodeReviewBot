package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.dto.row.AdminRow;
import com.walle.code.port.output.InsertAdminOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link InsertAdminOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceInsertAdminOutputPortAdapter implements InsertAdminOutputPort {
	public static final String QUERY = "insert into t_admin(id_user) values(:userId)";
	public static final String PARAM_USER_ID = "userId";

	@NonNull
	private final EntityManager entityManager;

	@Override
	@NonNull
	public void insertAdmin(@NonNull AdminRow admin) {
		this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_USER_ID, admin.getUserId().getValue())
				.executeUpdate();
	}
}
