package com.walle.code.router;

import com.walle.code.handler.*;
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
public final class DiscordMessageRouter extends ListenerAdapter {
	public static final String CODE_PREFIX = "```";
	public static final String REGISTER_COMMAND = "!register";
	public static final String REGISTER_REVIEWER_COMMAND = "!reviewer";
	public static final String APPROVE_COMMAND_PREFIX = "!approve";
	public static final String CODE_REVIEW_PREFIX = "!codeReview";
	public static final String BOT_ERROR_MESSAGE = "Sorry, we are not handle message from another bots";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Oops. We fix this bug";

//	@NonNull
//	private final CreateSessionHandler createSessionHandler;

	@NonNull
	private final RegisterStudentHandler registerStudentHandler;

	@NonNull
	private final RegisterReviewerHandler registerReviewerHandler;

	@NonNull
	private final ApproveReviewerHandler approveReviewerHandler;

	@NonNull
	private final ReviewCodeHandler reviewCodeHandler;

	@Override
	public void onMessageReceived(@NonNull MessageReceivedEvent event) {
		try {
			/**
			 * @todo: добавить proxy слой, которой будет понимать что ему необходимо вызывать в зависимости
			 * @todo: от содержимого. Здесь оставить только
			 * @todo: event.getChannel().sendMessage(this.proxyDiscordEventHandler.handle(event)).queue()
			 * @todo: и блок try-catch в случае непредвиденной ошибки сервера.
			 * @todo: нарушается SRP (single-responsibility principle)
			 */
//			if (event.getAuthor().isBot()) {
//				event.getChannel().sendMessage(BOT_ERROR_MESSAGE).queue();
//			}

//			if (event.getMessage().getContentRaw().startsWith(CODE_PREFIX)) {
//				event.getChannel().sendMessage(this.createSessionHandler.handle(event)).queue();
//
//			}

			if (event.getMessage().getContentRaw().equalsIgnoreCase(REGISTER_COMMAND)) {
				event.getChannel().sendMessage(this.registerStudentHandler.handle(event)).queue();
			}

			if (event.getMessage().getContentRaw().equalsIgnoreCase(REGISTER_REVIEWER_COMMAND)) {
				event.getChannel().sendMessage(this.registerReviewerHandler.handle(event)).queue();
			}

			if (event.getMessage().getContentRaw().startsWith(APPROVE_COMMAND_PREFIX)) {
				event.getChannel().sendMessage(this.approveReviewerHandler.handle(event)).queue();
			}

			if (event.getMessage().getContentRaw().startsWith(CODE_REVIEW_PREFIX)) {
				event.getChannel().sendMessage(this.reviewCodeHandler.handle(event)).queue();
			}
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
			event.getChannel().sendMessage(INTERNAL_SERVER_ERROR_MESSAGE).queue();
		}
	}
}
