package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.domain.id.UserId;
import com.walle.code.port.output.UpdateUserChatIdByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

/**
 * Реализация {@link UpdateUserChatIdByIdOutputPort} с использованием Javax Persistence
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceUpdateUserChatIdByIdOutputPortAdapter implements UpdateUserChatIdByIdOutputPort {
	public static final String QUERY = "update t_user set id_chat = :chatId where id = :id";
	public static final String PARAM_CHAT_ID = "chatId";
	public static final String PARAM_ID = "id";

	@NonNull
	private final EntityManager entityManager;

	@Override
	public void updateUserChatIdById(UserId userId, Long chatId) {
		this.entityManager.createNativeQuery(QUERY)
				.setParameter(PARAM_CHAT_ID, chatId)
				.setParameter(PARAM_ID, userId.getValue())
				.executeUpdate();
	}
}
