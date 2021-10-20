package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.dto.row.AdminRow;
import com.walle.code.port.output.FindAdminsOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Реализация {@link FindAdminsOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindAdminsOutputPortAdapter implements FindAdminsOutputPort {
	public static final String QUERY = "select id, id_user from t_admin";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<AdminRow> rowMapper;

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public List<AdminRow> findAdmins() {
		return (List<AdminRow>) this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.getResultList()
				.stream()
				.map(result -> this.rowMapper.mapRow((Tuple) result))
				.collect(toList());
	}
}
