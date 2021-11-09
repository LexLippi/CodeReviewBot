package com.walle.code.handler.discord;

import com.walle.code.command.AddReviewerProgrammingLanguage;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.AddReviewerProgrammingLanguageUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Компонент для отправки запроса на добавление языка программирования для ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class AddReviewerProgrammingLanguageHandler {
	public static final String SPACE = " ";
	public static final int SKIP_COUNT = 1;

	@NonNull
	private final AddReviewerProgrammingLanguageUseCase addReviewerProgrammingLanguageUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		return this.addReviewerProgrammingLanguageUseCase.addReviewerProgrammingLanguage(
				AddReviewerProgrammingLanguage.of(DiscordUserId.of(event.getAuthor().getId()),
						event.getAuthor().getName(),
						event.getMessage().getContentRaw().split(SPACE)[1]
		)).mapWith(AddReviewerProgrammingLanguagesResultToStringMapper.INSTANCE);
	}

	private enum AddReviewerProgrammingLanguagesResultToStringMapper
			implements AddReviewerProgrammingLanguage.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "Your add programming language query was successful handle." +
				" Admins connect with you in nearest time.";
		public static final String REVIEWER_PROGRAMMING_LANGUAGE_ALREADY_EXISTS_MESSAGE = "This programming language " +
				"already use for you.";
		public static final String REVIEWER_NOT_FOUND_MESSAGE = "You not register as reviewer in our bot. Please, " +
				"register with !reviewer command";
		@Override
		public String mapSuccess(AddReviewerProgrammingLanguage.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapReviewerProgrammingLanguageAlreadyExists(
				AddReviewerProgrammingLanguage.Result.ReviewerProgrammingLanguageAlreadyExists result) {
			return REVIEWER_PROGRAMMING_LANGUAGE_ALREADY_EXISTS_MESSAGE;
		}

		@Override
		public String mapReviewerNotFound(AddReviewerProgrammingLanguage.Result.ReviewerNotFound result) {
			return REVIEWER_NOT_FOUND_MESSAGE;
		}
	}
}
