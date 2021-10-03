package com.walle.code.listener;

import com.walle.code.command.CreateSession;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.CreateSessionUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/**
 * Обработчик сообщений из Discord.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class DiscordMessageListenerAdapter extends ListenerAdapter {
	public static final String CODE_PREFIX = "```";
	public static final String EMPTY_STRING = "";
	public static final String CODE_QUOTE = "`";
	public static final String LINE_BREAK = "\n";
	public static final int FIRST_LINE = 0;

	@NonNull
	private final CreateSessionUseCase createSessionUseCase;

	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		if (event.getMessage().getContentRaw().startsWith(CODE_PREFIX) && !event.getAuthor().isBot()) {
			// @todo: вынести всю внутреннюю обработку в отдельный компонент
			event.getChannel().sendMessage(this.createSessionUseCase.createSessionUseCase(CreateSession.of(
					DiscordUserId.of(event.getAuthor().getId()),
					event.getMessage().getContentRaw(),
					event.getMessage().getContentRaw().split(LINE_BREAK)[FIRST_LINE].replace(CODE_QUOTE, EMPTY_STRING)))
					.mapWith(CreateSessionResultToStringMapper.INSTANCE))
					.queue();
		}
	}

	private enum CreateSessionResultToStringMapper implements CreateSession.Result.Mapper<String> {
		INSTANCE;

		@Override
		public String mapSuccess(CreateSession.Result.Success result) {
			return "Congratulations, we create review session. Wait, result from your reviewer";
		}

		@Override
		public String mapReviewerNotFound(CreateSession.Result.ReviewerNotFound result) {
			return "Sorry, reviewer for you task not found. Maybe we have not reviewers " +
					"for this programming language. Try again later";
		}

		@Override
		public String mapProgrammingLanguageNotFound(CreateSession.Result.ProgrammingLanguageNotFound result) {
			return "Sorry, programming language by your alias not found. Send message to admin";
		}

		@Override
		public String mapStudentNotFound(CreateSession.Result.StudentNotFound result) {
			return "Sorry, student with you id not found. Please register at our bot. Send !register";
		}

		@Override
		public String mapUserNotFound(CreateSession.Result.UserNotFound result) {
			return "Sorry, user with you id not found. Please register at our bot. Send !register";
		}
	}
}
