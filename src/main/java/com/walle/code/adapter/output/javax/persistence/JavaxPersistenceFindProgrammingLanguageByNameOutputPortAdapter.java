package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.dto.row.ProgrammingLanguageRow;
import com.walle.code.port.output.FindProgrammingLanguageByNameOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
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
	public static final String QUERY = "select id, c_name from t_programming_language " +
			"where c_name like :programmingLanguageName";
	public static final String PARAM_PROGRAMMING_LANGUAGE_NAME = "programmingLanguageName";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<ProgrammingLanguageRow> rowMapper;

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public Optional<ProgrammingLanguageRow> findProgrammingLanguageByName(@NonNull String programmingLanguageName) {
		return this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.setParameter(PARAM_PROGRAMMING_LANGUAGE_NAME, programmingLanguageName)
				.getResultList()
				.stream()
				.findFirst()
				.map(result -> this.rowMapper.mapRow((Tuple) result));
	}
}
