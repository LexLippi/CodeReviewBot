package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.output.FindReviewerByDiscordIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Optional;

/**
 * Реализация {@link FindReviewerByDiscordIdOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindReviewerByDiscordIdOutputPortAdapter
		implements FindReviewerByDiscordIdOutputPort {
	public static final String QUERY = "select tr.id as id, tu.id as id_user from t_reviewer tr inner join t_user tu " +
			"on tr.id_user=tu.id where tu.id_discord = :discordId";
	public static final String PARAM_DISCORD_ID = "discordId";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<ReviewerRow> rowMapper;

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public Optional<ReviewerRow> findReviewerByDiscordId(DiscordUserId discordUserId) {
		return this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.setParameter(PARAM_DISCORD_ID, discordUserId.getValue())
				.getResultList()
				.stream()
				.findFirst()
				.map(result -> this.rowMapper.mapRow((Tuple) result));
	}
}
