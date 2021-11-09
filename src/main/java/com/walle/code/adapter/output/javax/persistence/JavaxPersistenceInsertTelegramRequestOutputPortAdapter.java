package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.UserId;
import com.walle.code.port.output.InsertTelegramRequestOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link InsertTelegramRequestOutputPort} с использованием Javax Persistence
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceInsertTelegramRequestOutputPortAdapter implements InsertTelegramRequestOutputPort {
	public static final String QUERY = "insert into t_telegram_request(id_user, c_telegram_nickname) values(:userId, " +
			":telegramNickname)";
	public static final String PARAM_USER_ID = "userId";
	public static final String PARAM_TELEGRAM_NICKNAME = "telegramNickname";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public void insertTelegramRequest(UserId userId, String telegramNickname) {
		this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_USER_ID, userId.getValue())
				.setParameter(PARAM_TELEGRAM_NICKNAME, telegramNickname)
				.executeUpdate();
	}
}
