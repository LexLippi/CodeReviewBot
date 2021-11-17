package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.ReviewerId;
import com.walle.code.port.output.ReviewerProgrammingLanguageExistOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link ReviewerProgrammingLanguageExistOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceReviewerProgrammingLanguageExistOutputPortAdapter
		implements ReviewerProgrammingLanguageExistOutputPort {
	public static final String QUERY = "select 1 from t_reviewer_programming_language " +
			"where id_reviewer = :reviewerId and id_programming_language = (" +
			"select tpl.id from t_programming_language tpl " +
			"inner join t_programming_language_alias tpla on tpl.id=tpla.id_programming_language " +
			"where tpla.c_alias ilike :alias)";
	public static final String PARAM_ALIAS = "alias";
	public static final String PARAM_REVIEWER_ID = "reviewerId";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public boolean reviewerProgrammingLanguageExist(@NonNull ReviewerId reviewerId,
													@NonNull String programmingLanguageAlias) {
		return !entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_ALIAS, programmingLanguageAlias)
				.setParameter(PARAM_REVIEWER_ID, reviewerId.getValue())
				.getResultList()
				.isEmpty();
	}
}
