package com.walle.code.handler;

import com.walle.code.command.CreateSession;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.CreateSessionUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

/**
 * Компонент для обработки создания сеанса код-ревью.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class CreateSessionHandler {
	public static final String EMPTY_STRING = "";
	public static final String CODE_QUOTE = "`";
	public static final String LINE_BREAK = "\n";
	public static final int FIRST_LINE = 0;

	@NonNull
	private final CreateSessionUseCase createSessionUseCase;

	@NonNull
	public String handle(@Nonnull MessageReceivedEvent event) {
		return this.createSessionUseCase.createSessionUseCase(CreateSession.of(
				DiscordUserId.of(event.getAuthor().getId()),
				event.getMessage().getContentRaw(),
				event.getMessage().getContentRaw().split(LINE_BREAK)[FIRST_LINE].replace(CODE_QUOTE, EMPTY_STRING)))
				.mapWith(CreateSessionResultToStringMapper.INSTANCE);
	}

	private enum CreateSessionResultToStringMapper implements CreateSession.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "Congratulations, we create review session." +
				" Wait, result from your reviewer";
		public static final String REVIEWER_NOT_FOUND_MESSAGE = "Sorry, reviewer for you task not found. " +
				"Maybe we have not reviewers for this programming language. Try again later";
		public static final String PROGRAMMING_LANGUAGE_NOT_FOUND_MESSAGE =  "Sorry, programming language " +
				"by your alias not found. Send message to admin";
		public static final String STUDENT_NOT_FOUND_MESSAGE = "Sorry, student with you id not found. " +
				"Please register at our bot. Send !register";
		public static final String USER_NOT_FOUND_MESSAGE =  "Sorry, user with you id not found. " +
				"Please register at our bot. Send !register";

		@Override
		public String mapSuccess(CreateSession.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapReviewerNotFound(CreateSession.Result.ReviewerNotFound result) {
			return REVIEWER_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapProgrammingLanguageNotFound(CreateSession.Result.ProgrammingLanguageNotFound result) {
			return PROGRAMMING_LANGUAGE_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapStudentNotFound(CreateSession.Result.StudentNotFound result) {
			return STUDENT_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapUserNotFound(CreateSession.Result.UserNotFound result) {
			return USER_NOT_FOUND_MESSAGE;
		}
	}
}
