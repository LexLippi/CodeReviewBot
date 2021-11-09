package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.dto.row.SessionRow;
import com.walle.code.dto.status.SessionStatus;
import com.walle.code.port.output.FindAdjustmentSessionByURLLinkOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Optional;

/**
 * Реализация {@link FindAdjustmentSessionByURLLinkOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindAdjustmentSessionByURLLinkOutputPortAdapter
		implements FindAdjustmentSessionByURLLinkOutputPort {
	public static final String QUERY = "select id, id_reviewer, id_student, id_programming_language, c_status " +
			"from t_session where c_url_link ilike :urlLink and c_status = :status";
	public static final String PARAM_URL_LINK = "urlLink";
	public static final String PARAM_STATUS = "status";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<SessionRow> rowMapper;

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public Optional<SessionRow> findAdjustmentSessionByURLLink(@NonNull String urlLink) {
		return this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.setParameter(PARAM_URL_LINK, urlLink)
				.setParameter(PARAM_STATUS, SessionStatus.ADJUSTMENT.getTag())
				.getResultList()
				.stream()
				.findFirst()
				.map(result -> this.rowMapper.mapRow((Tuple) result));
	}
}
