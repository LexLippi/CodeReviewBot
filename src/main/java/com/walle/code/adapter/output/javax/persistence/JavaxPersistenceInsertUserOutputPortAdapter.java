package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.InsertUserOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link InsertUserOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceInsertUserOutputPortAdapter implements InsertUserOutputPort {
	public static final String QUERY = "insert into t_user(id_discord, c_nickname) " +
			"values(:discordUserId, :nickname) returning id";

	public static final String PARAM_DISCORD_USER_ID = "discordUserId";
	public static final String PARAM_NICKNAME = "nickname";

	@NonNull
	private final EntityManager entityManager;

	@Override
	@NonNull
	public UserId insertUser(@NonNull UserRow user) {
		return UserId.of((Integer) this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_DISCORD_USER_ID, user.getDiscordId().getValue())
				.setParameter(PARAM_NICKNAME, user.getNickname())
				.getSingleResult());
	}
}
