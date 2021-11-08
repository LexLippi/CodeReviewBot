package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.output.UpdateUserEmailByDiscordIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link UpdateUserEmailByDiscordIdOutputPort} с использованием Javax Persistence
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceUpdateUserEmailByDiscordIdOutputPortAdapter
		implements UpdateUserEmailByDiscordIdOutputPort {

	public static final String QUERY = "update t_user set c_email = :email where id_discord = :discordId";
	public static final String PARAM_EMAIL = "email";
	public static final String PARAM_DISCORD_ID = "discordId";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public void updateUserEmailByDiscordId(@NonNull String email, @NonNull DiscordUserId discordUserId) {
		this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_EMAIL, email)
				.setParameter(PARAM_DISCORD_ID, discordUserId.getValue())
				.executeUpdate();
	}
}
