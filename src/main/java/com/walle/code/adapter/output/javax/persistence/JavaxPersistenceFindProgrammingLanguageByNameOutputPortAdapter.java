package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.dto.row.ProgrammingLanguageRow;
import com.walle.code.port.output.FindProgrammingLanguageByNameOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Реализация {@link FindProgrammingLanguageByNameOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindProgrammingLanguageByNameOutputPortAdapter
		implements FindProgrammingLanguageByNameOutputPort {
	public static final String QUERY = "select * from v_programming_language where c_name " +
			"ilike :programmingLanguageName";
	public static final String PARAM_PROGRAMMING_LANGUAGE_NAME = "programmingLanguageName";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public Optional<ProgrammingLanguageRow> findProgrammingLanguageByName(String programmingLanguageName) {
		return this.entityManager.createNamedQuery(QUERY, ProgrammingLanguageRow.class)
				.setParameter(PARAM_PROGRAMMING_LANGUAGE_NAME, programmingLanguageName)
				.getResultList()
				.stream()
				.findFirst();
	}
}
