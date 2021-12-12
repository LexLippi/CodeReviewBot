package com.walle.code.adapter.output.jda;

import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.SendMessageOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;

/**
 * Реализация {@link SendMessageOutputPort} с использованием JDA.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class JdaSendMessageOutputPortAdapter implements SendMessageOutputPort {
	@NonNull
	private final JDA jda;

	@Override
	public void sendMessage(@NonNull UserRow user, @NonNull String text) {
		if (user.getDiscordId() == null) {
			return;
		}

		this.jda.retrieveUserById(user.getDiscordId().getValue())
				.queue(discordUser -> discordUser.openPrivateChannel()
						.queue(privateChannel -> privateChannel.sendMessage(text)
								.queue()));
	}
}
