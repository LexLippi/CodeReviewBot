package com.walle.code.adapter.output.jda;

import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.output.SendMessageByDiscordIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;

import java.util.Objects;

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
	public void sendMessageToReviewer(DiscordUserId discordUserId, String message) {
		Objects.requireNonNull(this.jda.getUserById(discordUserId.getValue()))
				.openPrivateChannel()
				.queue(privateChannel -> privateChannel.sendMessage(message).queue());
	}
}
