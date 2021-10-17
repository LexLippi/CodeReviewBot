package com.walle.code.handler;

import com.walle.code.command.RegisterReviewer;
import com.walle.code.command.RegisterStudent;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.RegisterReviewerUseCase;
import com.walle.code.port.input.RegisterStudentUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Компонент для обработки регистрации пользователя.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class RegisterReviewerHandler {

	@NonNull
	private final RegisterReviewerUseCase registerReviewerUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		return this.registerReviewerUseCase.registerReviewer(RegisterReviewer.of(
				DiscordUserId.of(event.getAuthor().getId()),
				event.getAuthor().getName()))
				.mapWith(RegisterUserResultToStringMapper.INSTANCE);
	}

	private enum RegisterUserResultToStringMapper implements RegisterReviewer.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "Your registration query was successful handle." +
				" Admins connect with you in nearest time";
		public static final String REVIEWER_ALREADY_REGISTER_MESSAGE = "You already register in our bot as reviewer";

		@Override
		public String mapSuccess(RegisterReviewer.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapReviewerAlreadyRegister(RegisterReviewer.Result.ReviewerAlreadyRegister result) {
			return REVIEWER_ALREADY_REGISTER_MESSAGE;
		}
	}
}
