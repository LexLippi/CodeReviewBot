package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.output.FindReviewerByUserIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Optional;

/**
 * Реализация {@link FindReviewerByUserIdOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindReviewerByUserIdOutputPortAdapter implements FindReviewerByUserIdOutputPort {
	public static final String QUERY = "select id, id_user from t_reviewer where id_user = :userId";
	public static final String PARAM_USER_ID = "userId";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<ReviewerRow> rowMapper;

	@Override
	@NonNull
	public Optional<ReviewerRow> findReviewerByUserId(@NonNull UserId userId) {
		return this.entityManager.createQuery(QUERY, Tuple.class)
				.setParameter(PARAM_USER_ID, userId.getValue())
				.getResultList()
				.stream()
				.findFirst()
				.map(this.rowMapper::mapRow);
	}
}
