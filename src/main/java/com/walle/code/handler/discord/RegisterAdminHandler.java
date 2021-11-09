package com.walle.code.handler.discord;

import com.walle.code.command.RegisterAdmin;
import com.walle.code.command.RegisterReviewer;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.RegisterAdminUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

/**
 * Компонент для обработки регистрации администратора.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class RegisterAdminHandler {
	@NonNull
	private final RegisterAdminUseCase registerAdminUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		return this.registerAdminUseCase.registerAdmin(RegisterAdmin.of(
				DiscordUserId.of(event.getAuthor().getId()),
				event.getAuthor().getName()))
				.mapWith(RegisterAdminResultToStringMapper.INSTANCE);
	}

	private enum RegisterAdminResultToStringMapper implements RegisterAdmin.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = "Your registration query was successful handle." +
				" Admins connect with you in nearest time";
		public static final String ADMIN_ALREADY_REGISTER_MESSAGE = "You already register in our bot as admin";

		@Override
		public String mapSuccess(RegisterAdmin.Result.Success result) {
			return SUCCESS_MESSAGE;
		}

		@Override
		public String mapAdminAlreadyRegister(RegisterAdmin.Result.AdminAlreadyRegister result) {
			return ADMIN_ALREADY_REGISTER_MESSAGE;
		}
	}
}
