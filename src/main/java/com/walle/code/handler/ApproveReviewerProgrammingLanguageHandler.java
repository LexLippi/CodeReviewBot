package com.walle.code.handler;

import com.walle.code.command.ApproveReviewerProgrammingLanguage;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.ApproveReviewerProgrammingLanguageUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Компонент для подтверждения добавления языка программирования ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ApproveReviewerProgrammingLanguageHandler {
	public static final String SPACE = " ";

	@NonNull
	private final ApproveReviewerProgrammingLanguageUseCase approveReviewerProgrammingLanguageUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		var lines = event.getMessage().getContentRaw().lines().collect(toList());
		if (lines.size() < 2) {
			throw new IllegalArgumentException();
		}

		return this.approveReviewerProgrammingLanguageUseCase.approveReviewerProgrammingLanguage(
				ApproveReviewerProgrammingLanguage.of(
						DiscordUserId.of(event.getAuthor().getId()),
						Arrays.stream(lines.get(0).split(SPACE)).skip(1).collect(joining(SPACE)),
						lines.get(1)
				)).mapWith(ApproveReviewerProgrammingLanguageResultToStringMapper.INSTANCE);
	}

	private enum ApproveReviewerProgrammingLanguageResultToStringMapper
			implements ApproveReviewerProgrammingLanguage.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "You successfully approved programming language for this reviewer";
		public static final String ADMIN_NOT_FOUND_MESSAGE = "You don't admin in our bot";
		public static final String REVIEWER_NOT_FOUND_MESSAGE = "You not register as reviewer in our bot. Please, " +
				"register with !reviewer command";
		public static final String REVIEWER_USER_NOT_FOUND_MESSAGE = "You not register in our bot. Please, register " +
				"with !register command";
		public static final String PROGRAMMING_LANGUAGE_NOT_FOUND_MESSAGE = "This programming language not found";

		@Override
		public String mapSuccess(ApproveReviewerProgrammingLanguage.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapAdminNotFound(ApproveReviewerProgrammingLanguage.Result.AdminNotFound result) {
			return ADMIN_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapReviewerUserNotFound(ApproveReviewerProgrammingLanguage.Result.ReviewerUserNotFound result) {
			return REVIEWER_USER_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapReviewerNotFound(ApproveReviewerProgrammingLanguage.Result.ReviewerNotFound result) {
			return REVIEWER_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapProgrammingLanguageNotFound(
				ApproveReviewerProgrammingLanguage.Result.ProgrammingLanguageNotFound result) {
			return PROGRAMMING_LANGUAGE_NOT_FOUND_MESSAGE;
		}
	}
}
