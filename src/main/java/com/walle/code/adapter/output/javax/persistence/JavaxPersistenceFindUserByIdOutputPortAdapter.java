package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.FindUserByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;

/**
 * Реализация {@link FindUserByIdOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindUserByIdOutputPortAdapter implements FindUserByIdOutputPort {
	public static final String QUERY = "select id, id_discord, c_nickname, c_first_name, c_surname, c_email, id_chat " +
			"from t_user where id = :id";
	public static final String PARAM_ID = "id";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<UserRow> rowMapper;

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public UserRow findUserById(@NonNull UserId id) {
		return (UserRow) this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.setParameter(PARAM_ID, id.getValue())
				.getResultList()
				.stream()
				.findFirst()
				.map(result -> this.rowMapper.mapRow((Tuple) result))
				.orElseThrow();
	}
}
