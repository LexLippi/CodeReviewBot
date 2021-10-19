package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.FindUserByDiscordIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Optional;

/**
 * Реализация {@link FindUserByDiscordIdOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindUserByDiscordIdOutputPortAdapter implements FindUserByDiscordIdOutputPort {
	public static final String QUERY = "select id, id_discord, c_nickname, c_first_name, c_surname, c_email " +
			"from t_user where id_discord = :discordId";
	public static final String PARAM_DISCORD_ID = "discordId";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<UserRow> rowMapper;

	@Override
	@NonNull
	public Optional<UserRow> findUserByDiscordId(@NonNull DiscordUserId discordId) {
		return this.entityManager.createNativeQuery(QUERY, Tuple.class)
				.setParameter(PARAM_DISCORD_ID, discordId.getValue())
				.getResultList()
				.stream()
				.findFirst()
				.map(result -> this.rowMapper.mapRow((Tuple) result));
	}
}
