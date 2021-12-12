package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.UserId;
import com.walle.code.port.output.FindUserIdByTelegramNicknameOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Реализация {@link FindUserIdByTelegramNicknameOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindUserIdByTelegramNicknameOutputPortAdapter
		implements FindUserIdByTelegramNicknameOutputPort {
	public static final String QUERY = "select id_user from t_telegram_request " +
			"where c_telegram_nickname ilike :nickname";
	public static final String PARAM_NICKNAME = "nickname";

	@NonNull
	private final EntityManager entityManager;

	@Override
	@NonNull
	public Optional<UserId> findUserIdByTelegramNicknameOutputPort(@NonNull String telegramNickname) {
		return this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_NICKNAME, telegramNickname)
				.getResultList()
				.stream()
				.findFirst()
				.map(value -> UserId.of((Integer) value));
	}
}
