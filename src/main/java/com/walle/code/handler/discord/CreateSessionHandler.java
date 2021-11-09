package com.walle.code.handler.discord;

import com.walle.code.command.CreateSession;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.CreateSessionUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Компонент для обработки создания сеанса код-ревью.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class CreateSessionHandler {
	public static final String EMPTY_STRING = "";
	public static final String SPACE = " ";
	public static final String CODE_QUOTE = "`";
	public static final String LINE_BREAK = "\n";
	public static final int FIRST_LINE = 0;
	public static final int SECOND_LINE = 1;

	@NonNull
	private final CreateSessionUseCase createSessionUseCase;

	@NonNull
	public String handle(@Nonnull MessageReceivedEvent event) {
		var lines = event.getMessage().getContentRaw().lines().collect(toList());

		if (lines.size() < 2) {
			throw new IllegalArgumentException();
		}

		return this.createSessionUseCase.createSessionUseCase(CreateSession.of(
				DiscordUserId.of(event.getAuthor().getId()),
				lines.stream().skip(1).collect(joining(LINE_BREAK)),
				Arrays.stream(lines.get(FIRST_LINE).split(SPACE)).skip(1).collect(joining(SPACE)),
				lines.get(SECOND_LINE).replace(CODE_QUOTE, EMPTY_STRING)))
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
