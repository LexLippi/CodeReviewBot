package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.FindUserByIdInOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Реализация {@link FindUserByIdInOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindUserByIdInOutputPortAdapter implements FindUserByIdInOutputPort {
	public static final String QUERY = "select id, id_discord, c_nickname, c_first_name, c_surname, c_email, id_chat " +
			"from t_user where id in (:ids)";
	public static final String PARAM_IDS = "ids";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<UserRow> rowMapper;

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public List<UserRow> findUserByIdIn(@NonNull Collection<UserId> ids) {
		if (ids.isEmpty()) {
			return List.of();
		}

		return (List<UserRow>) this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.setParameter(PARAM_IDS, ids.stream().map(UserId::getValue).collect(toSet()))
				.getResultList()
				.stream()
				.map(result -> this.rowMapper.mapRow((Tuple) result))
				.collect(toList());
	}
}
