package com.walle.code.adapter.output.jda;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.output.SendMessageByDiscordIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;

/**
 * Реализация {@link SendMessageByDiscordIdOutputPort} с использованием JDA.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JdaSendMessageByDiscordIdOutputPortAdapter implements SendMessageByDiscordIdOutputPort {
	@NonNull
	private final JDA jda;

	@Override
	public void sendMessageByDiscordId(@NonNull DiscordUserId discordUserId, @NonNull String message) {
		this.jda.retrieveUserById(discordUserId.getValue())
				.queue(user -> user.openPrivateChannel()
						.queue(privateChannel -> privateChannel.sendMessage(message).queue()));
	}
}
