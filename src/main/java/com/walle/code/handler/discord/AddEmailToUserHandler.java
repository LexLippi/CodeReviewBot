package com.walle.code.handler.discord;

import com.walle.code.command.AddEmailToUser;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.AddEmailToUserUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Компонент для добавления электронной почты к пользователю.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class AddEmailToUserHandler {
	public static final String SPACE = " ";
	public static final int EMAIL_ELEMENT = 1;

	@NonNull
	private final AddEmailToUserUseCase addEmailToUserUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		return this.addEmailToUserUseCase.addEmailToUser(AddEmailToUser.of(DiscordUserId.of(event.getAuthor().getId()),
				event.getMessage().getContentRaw().split(SPACE)[EMAIL_ELEMENT]))
				.mapWith(AddEmailToUserResultToStringMapper.INSTANCE);
	}

	private enum AddEmailToUserResultToStringMapper implements AddEmailToUser.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "You email successfully added to your profile";
		public static final String EMAIL_IS_NOT_CORRECT_MESSAGE = "Email address which you input is not correct email";

		@Override
		public String mapSuccess(AddEmailToUser.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapEmailIsNotCorrect(AddEmailToUser.Result.EmailIsNotCorrect result) {
			return EMAIL_IS_NOT_CORRECT_MESSAGE;
		}
	}
}
