package com.walle.code.handler.discord;

import com.walle.code.command.RegisterStudent;
import com.walle.code.domain.id.DiscordUserId;
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
public final class RegisterStudentHandler {

	@NonNull
	private final RegisterStudentUseCase registerStudentUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		return this.registerStudentUseCase.registerStudent(RegisterStudent.of(
				DiscordUserId.of(event.getAuthor().getId()),
				event.getAuthor().getName()))
				.mapWith(RegisterUserResultToStringMapper.INSTANCE);
	}

	private enum RegisterUserResultToStringMapper implements RegisterStudent.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "You registration was success!";
		public static final String STUDENT_ALREADY_REGISTER_MESSAGE = "You already register in our bot";

		@Override
		public String mapSuccess(RegisterStudent.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapStudentAlreadyRegister(RegisterStudent.Result.StudentAlreadyRegister result) {
			return STUDENT_ALREADY_REGISTER_MESSAGE;
		}
	}
}
