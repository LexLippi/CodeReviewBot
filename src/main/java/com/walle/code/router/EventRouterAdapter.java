package com.walle.code.router;

import com.walle.code.handler.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Реализация {@link EventRouter}
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class EventRouterAdapter implements EventRouter {
	public static final String CODE_PREFIX = "```";
	public static final String REGISTER_COMMAND = "!register";
	public static final String REGISTER_REVIEWER_COMMAND = "!reviewer";
	public static final String APPROVE_COMMAND_PREFIX = "!approve";
	public static final String CODE_REVIEW_PREFIX = "!codeReview";

	@NonNull
	private final CreateSessionHandler createSessionHandler;

	@NonNull
	private final RegisterStudentHandler registerStudentHandler;

	@NonNull
	private final RegisterReviewerHandler registerReviewerHandler;

	@NonNull
	private final ApproveReviewerHandler approveReviewerHandler;

	@NonNull
	private final ReviewCodeHandler reviewCodeHandler;

	@Override
	public String routingEvent(MessageReceivedEvent event) {
		if (event.getMessage().getContentRaw().startsWith(CODE_PREFIX)) {
			return this.createSessionHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().startsWith(REGISTER_COMMAND)) {
			return this.registerStudentHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().startsWith(REGISTER_REVIEWER_COMMAND)) {
			return this.registerReviewerHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().startsWith(APPROVE_COMMAND_PREFIX)) {
			return this.approveReviewerHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().startsWith(CODE_REVIEW_PREFIX)) {
			return this.reviewCodeHandler.handle(event);
		}

		throw new IllegalArgumentException();
	}
}
