package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.dto.row.AdminRow;
import com.walle.code.port.output.FindAdminByDiscordUserIdOutputPort;
import com.walle.code.port.output.FindAdminsOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Реализация {@link FindAdminsOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindAdminByDiscordUserIdOutputPortAdapter
		implements FindAdminByDiscordUserIdOutputPort {
	public static final String QUERY = "select ta.id as id, tu.id as id_user from t_admin ta inner join t_user tu " +
			"on ta.id_user=tu.id where tu.id_discord = :discordId";
	public static final String PARAM_DISCORD_ID = "discordId";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<AdminRow> rowMapper;

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public Optional<AdminRow> findAdminByDiscordUserId(DiscordUserId discordUserId) {
		return this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.setParameter(PARAM_DISCORD_ID, discordUserId.getValue())
				.getResultList()
				.stream()
				.findFirst()
				.map(result -> this.rowMapper.mapRow((Tuple) result));
	}
}
