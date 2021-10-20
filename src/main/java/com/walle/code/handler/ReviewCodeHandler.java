package com.walle.code.handler;

import com.walle.code.command.ReviewCode;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.dto.status.TaskStatus;
import com.walle.code.port.input.ReviewCodeUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

/**
 * Компонент для обработки сохранения результатов код-ревью
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ReviewCodeHandler {
	public static final String LINE_BREAK = "\n";
	public static final String SPACE = " ";

	@NonNull
	private final ReviewCodeUseCase reviewCodeUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		var lines = event.getMessage().getContentRaw().split(LINE_BREAK);
		var header = lines[0].split(SPACE);
		return this.reviewCodeUseCase.reviewCode(ReviewCode.of(
				DiscordUserId.of(event.getAuthor().getId()),
				header[1],
				TaskStatus.fromTag(header[2]),
				String.join(LINE_BREAK, Arrays.stream(lines).skip(1).toArray(String[]::new))))
				.mapWith(ReviewCodeResultToStringMapper.INSTANCE);
	}

	private enum ReviewCodeResultToStringMapper implements ReviewCode.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "Comment for this task added successfully";
		public static final String SESSION_NOT_FOUND_MESSAGE = "Code review session between you and user not found";
		public static final String REVIEWER_NOT_FOUND = "Sorry, you haven't access to this method";
		public static final String STUDENT_NOT_FOUND_MESSAGE = "Sorry, student with this nickname not found";
		public static final String USER_NOT_FOUND = "Sorry, user with this nickname not found";

		@Override
		public String mapSuccess(ReviewCode.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapSessionNotFound(ReviewCode.Result.SessionNotFound result) {
			return SESSION_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapReviewerNotFound(ReviewCode.Result.ReviewerNotFound result) {
			return REVIEWER_NOT_FOUND;
		}

		@Override
		public String mapStudentNotFound(ReviewCode.Result.StudentNotFound result) {
			return STUDENT_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapUserNotFound(ReviewCode.Result.UserNotFound result) {
			return USER_NOT_FOUND;
		}
	}
}
