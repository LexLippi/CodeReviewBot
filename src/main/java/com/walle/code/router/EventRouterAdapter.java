package com.walle.code.router;

import com.walle.code.handler.discord.*;
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
	public static final String SUBMIT_PREFIX = "!submit";
	public static final String REGISTER_COMMAND = "!register";
	public static final String REGISTER_REVIEWER_COMMAND = "!reviewer";
	public static final String APPROVE_COMMAND_PREFIX = "!approve_reviewer";
	public static final String CODE_REVIEW_PREFIX = "!code_review";
	public static final String ADD_PROGRAMMING_LANGUAGE_PREFIX = "!add_programming_language";
	public static final String APPROVE_REVIEWER_PROGRAMMING_LANGUAGE = "!approve_programming_language";
	public static final String DELETE_PROGRAMMING_LANGUAGE_PREFIX = "!delete_programming_language";
	public static final String ADD_EMAIL_PREFIX = "!add_email";
	public static final String ADD_TELEGRAM_PREFIX = "!add_telegram";
	public static final String REGISTER_ADMIN_COMMAND = "!admin";
	public static final String APPROVE_ADMIN_REGISTER_COMMAND = "!approve_admin";

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

	@NonNull
	private final AddReviewerProgrammingLanguageHandler addReviewerProgrammingLanguageHandler;

	@NonNull
	private final DeleteProgrammingLanguageHandler deleteProgrammingLanguageHandler;

	@NonNull
	private final ApproveReviewerProgrammingLanguageHandler approveReviewerProgrammingLanguageHandler;

	@NonNull
	private final AddEmailToUserHandler addEmailToUserHandler;

	@NonNull
	private final CreateRequestToAddTelegramHandler createRequestToAddTelegramHandler;

	@NonNull
	private final RegisterAdminHandler registerAdminHandler;

	@NonNull
	private final ApproveAdminHandler approveAdminHandler;

	@Override
	public String routingEvent(MessageReceivedEvent event) {
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(SUBMIT_PREFIX)) {
			return this.createSessionHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(REGISTER_COMMAND)) {
			return this.registerStudentHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(REGISTER_REVIEWER_COMMAND)) {
			return this.registerReviewerHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(APPROVE_COMMAND_PREFIX)) {
			return this.approveReviewerHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(CODE_REVIEW_PREFIX)) {
			return this.reviewCodeHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(ADD_PROGRAMMING_LANGUAGE_PREFIX)) {
			return this.addReviewerProgrammingLanguageHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(DELETE_PROGRAMMING_LANGUAGE_PREFIX)) {
			return this.deleteProgrammingLanguageHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(APPROVE_REVIEWER_PROGRAMMING_LANGUAGE)) {
			return this.approveReviewerProgrammingLanguageHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(ADD_EMAIL_PREFIX)) {
			return this.addEmailToUserHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(ADD_TELEGRAM_PREFIX)) {
			return this.createRequestToAddTelegramHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(REGISTER_ADMIN_COMMAND)) {
			return this.registerAdminHandler.handle(event);
		}

		if (event.getMessage().getContentRaw().toLowerCase().startsWith(APPROVE_ADMIN_REGISTER_COMMAND)) {
			return this.approveAdminHandler.handle(event);
		}

		throw new IllegalArgumentException();
	}
}
