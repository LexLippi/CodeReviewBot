package com.walle.code.handler;

import com.walle.code.command.DeleteProgrammingLanguage;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.DeleteProgrammingLanguageUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Компонент для удаления языка программирования для ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class DeleteProgrammingLanguageHandler {
	public static final String SPACE = " ";

	@NonNull
	private final DeleteProgrammingLanguageUseCase deleteProgrammingLanguageUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		return this.deleteProgrammingLanguageUseCase.deleteProgrammingLanguage(DeleteProgrammingLanguage.of(
				DiscordUserId.of(event.getAuthor().getId()),
				event.getMessage().getContentRaw().split(SPACE)[1]
		)).mapWith(DeleteProgrammingLanguageResultToStringMapper.INSTANCE);
	}

	private enum DeleteProgrammingLanguageResultToStringMapper
			implements DeleteProgrammingLanguage.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "This programming language sucess delete from your reviewers " +
				"programming language";
		public static final String REVIEWER_NOT_FOUND_MESSAGE = "You not register as reviewer in our bot. Please, " +
				"register with !reviewer command";
		public static final String PROGRAMMING_LANGUAGE_NOT_FOUND_MESSAGE = "Programming language not found in " +
				"our bot. Please, connect with our admins";

		@Override
		public String mapSuccess(DeleteProgrammingLanguage.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapReviewerNotFound(DeleteProgrammingLanguage.Result.ReviewerNotFound result) {
			return REVIEWER_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapProgrammingLanguageNotFound(
				DeleteProgrammingLanguage.Result.ProgrammingLanguageNotFound result) {
			return PROGRAMMING_LANGUAGE_NOT_FOUND_MESSAGE;
		}
	}
}
