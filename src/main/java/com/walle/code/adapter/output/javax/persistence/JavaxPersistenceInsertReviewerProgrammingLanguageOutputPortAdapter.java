package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.ReviewerProgrammingLanguageId;
import com.walle.code.dto.row.ReviewerProgrammingLanguageRow;
import com.walle.code.port.output.InsertReviewerProgrammingLanguageOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link InsertReviewerProgrammingLanguageOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceInsertReviewerProgrammingLanguageOutputPortAdapter
		implements InsertReviewerProgrammingLanguageOutputPort {
	public static final String QUERY = "insert into t_reviewer_programming_language(id_reviewer, " +
			"id_programming_language) values(:reviewerId, :programmingLanguageId) returning id";
	public static final String PARAM_REVIEWER_ID = "reviewerId";
	public static final String PARAM_PROGRAMMING_LANGUAGE_ID = "programmingLanguageId";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public ReviewerProgrammingLanguageId insertReviewerProgrammingLanguage(
			@NonNull ReviewerProgrammingLanguageRow reviewerProgrammingLanguage) {
		return ReviewerProgrammingLanguageId.of((Integer) this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_REVIEWER_ID, reviewerProgrammingLanguage.getReviewerId().getValue())
				.setParameter(PARAM_PROGRAMMING_LANGUAGE_ID, reviewerProgrammingLanguage.getProgrammingLanguageId()
						.getValue())
				.getSingleResult());
	}
}
