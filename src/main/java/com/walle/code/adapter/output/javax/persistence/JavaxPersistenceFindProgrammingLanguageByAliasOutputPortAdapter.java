package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.dto.row.ProgrammingLanguageRow;
import com.walle.code.port.output.FindProgrammingLanguageByAliasOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Optional;

/**
 * Реализация {@link FindProgrammingLanguageByAliasOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindProgrammingLanguageByAliasOutputPortAdapter
		implements FindProgrammingLanguageByAliasOutputPort {
	public static final String QUERY = "select id, c_name from t_programming_language tpl " +
			"inner join t_programming_language_alias tpla on tpl.id=tpla.id_programming_language " +
			"where c_alias ilike :alias";
	public static final String PARAM_ALIAS = "alias";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<ProgrammingLanguageRow> rowMapper;

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public Optional<ProgrammingLanguageRow> findProgrammingLanguageByAlias(@NonNull String alias) {
		return this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.setParameter(PARAM_ALIAS, alias)
				.getResultList()
				.stream()
				.findFirst()
				.map(result -> this.rowMapper.mapRow((Tuple) result));
	}
}
