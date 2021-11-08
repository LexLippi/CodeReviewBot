package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.domain.id.ReviewerId;
import com.walle.code.port.output.DeleteReviewerProgrammingLanguageOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link DeleteReviewerProgrammingLanguageOutputPort}
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceDeleteReviewerProgrammingLanguageOutputPortAdapter
		implements DeleteReviewerProgrammingLanguageOutputPort {
	public static final String QUERY = "delete from t_reviewer_programming_language where id_reviewer = :reviewerId " +
			"and id_programming_language = :programmingLanguageId";
	public static final String PARAM_REVIEWER_ID = "reviewerId";
	public static final String PARAM_PROGRAMMING_LANGUAGE_ID = "programmingLanguageId";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public void deleteReviewerProgrammingLanguage(@NonNull ReviewerId reviewerId,
												  @NonNull ProgrammingLanguageId programmingLanguageId) {
		this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_REVIEWER_ID, reviewerId.getValue())
				.setParameter(PARAM_PROGRAMMING_LANGUAGE_ID, programmingLanguageId.getValue())
				.executeUpdate();
	}
}
