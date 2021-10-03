package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.FindUserByDiscordIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Реализация {@link FindUserByDiscordIdOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindUserByDiscordIdOutputPortAdapter implements FindUserByDiscordIdOutputPort {
	public static final String QUERY = "select * from v_user where id_discord = :discordId";
	public static final String PARAM_DISCORD_ID = "discordId";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public Optional<UserRow> findUserByDiscordId(DiscordUserId discordId) {
		// @todo: read about named query
		return this.entityManager.createNamedQuery(QUERY, UserRow.class)
				.setParameter(PARAM_DISCORD_ID, discordId.getValue())
				.getResultList()
				.stream()
				.findFirst();
	}
}
