package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.domain.id.ReviewerId;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.output.FindReviewerByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;

/**
 * Реализация {@link FindReviewerByIdOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindReviewerByIdOutputPortAdapter implements FindReviewerByIdOutputPort {
	public static final String QUERY = "select id, id_user from t_reviewer where id = :id";
	public static final String PARAM_ID = "id";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<ReviewerRow> rowMapper;

	@Override
	@NonNull
	public ReviewerRow findReviewerById(@NonNull ReviewerId id) {
		return this.entityManager.createQuery(QUERY, Tuple.class)
				.setParameter(PARAM_ID, id.getValue())
				.getResultList()
				.stream()
				.findFirst()
				.map(this.rowMapper::mapRow)
				.orElseThrow();
	}
}
