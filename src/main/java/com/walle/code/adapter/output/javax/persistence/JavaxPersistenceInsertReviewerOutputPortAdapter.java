package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.ReviewerId;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.output.InsertReviewerOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link InsertReviewerOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceInsertReviewerOutputPortAdapter implements InsertReviewerOutputPort {
	public static final String QUERY = "insert into t_reviewer(id_user) values(:userId) returning id";
	public static final String PARAM_USER_ID = "userId";

	@NonNull
	private final EntityManager entityManager;

	@Override
	@NonNull
	public ReviewerId insertReviewer(@NonNull ReviewerRow reviewer) {
		return ReviewerId.of((Integer) this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_USER_ID, reviewer.getUserId().getValue())
				.getSingleResult());
	}
}
