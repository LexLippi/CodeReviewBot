package com.walle.code.adapter.output.javax.persistence;

import com.walle.code.adapter.output.row_mapper.RowMapper;
import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.FindUserByNicknameOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Optional;

/**
 * Реализация {@link FindUserByNicknameOutputPort} с использованием Javax Persistence.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JavaxPersistenceFindUserByNicknameOutputPortAdapter implements FindUserByNicknameOutputPort {
	public static final String QUERY = "select id, id_discord, c_nickname, c_first_name, c_surname, c_email " +
			"from t_user where c_nickname like :nickname";
	public static final String PARAM_NICKNAME = "nickname";

	@NonNull
	private final EntityManager entityManager;

	@NonNull
	private final RowMapper<UserRow> rowMapper;

	@Override
	@NonNull
	public Optional<UserRow> findUserByNickname(@NonNull String nickname) {
		return this.entityManager.createQuery(QUERY, Tuple.class)
				.setParameter(PARAM_NICKNAME, nickname)
				.getResultList()
				.stream()
				.findFirst()
				.map(this.rowMapper::mapRow);
	}
}
