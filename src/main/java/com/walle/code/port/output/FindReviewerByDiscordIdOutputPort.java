package com.walle.code.port.output;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.dto.row.ReviewerRow;

import java.util.Optional;

/**
 * Компонент для поиска ревьюера по идентификатору пользователя в Discord
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface FindReviewerByDiscordIdOutputPort {
	/**
	 * Метод поиска ревьюера по идентификатору пользователя в Discord
	 *
	 * @param discordUserId идентификатор пользователя в Discord
	 * @return              {@link Optional}, содержащий {@link ReviewerRow}, если ревьюер найден, иначе пустой.
	 */
	Optional<ReviewerRow> findReviewerByDiscordId(DiscordUserId discordUserId);
}
