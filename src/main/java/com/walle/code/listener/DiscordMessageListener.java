package com.walle.code.listener;

import com.walle.code.router.EventRouter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Обработчик сообщений из Discord.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
@Slf4j
public final class DiscordMessageListener extends ListenerAdapter {
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Oops. We fix this bug";

	@NonNull
	private final EventRouter eventRouter;

	@Override
	public void onMessageReceived(@NonNull MessageReceivedEvent event) {
		try {
			if (event.getAuthor().isBot()) {
				return;
			}
			event.getChannel().sendMessage(this.eventRouter.routingEvent(event)).queue();
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
			event.getChannel().sendMessage(INTERNAL_SERVER_ERROR_MESSAGE).queue();
		}
	}
}
