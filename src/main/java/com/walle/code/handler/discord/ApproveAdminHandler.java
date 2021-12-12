package com.walle.code.handler.discord;

import com.walle.code.command.ApproveAdmin;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.ApproveAdminUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * Компонент для подтверждения регистрации ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ApproveAdminHandler {
	public static final String SPACE = " ";

	@NonNull
	private final ApproveAdminUseCase approveAdminUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {
		return this.approveAdminUseCase.approveAdmin(ApproveAdmin.of(
				DiscordUserId.of(event.getAuthor().getId()),
				Arrays.stream(event.getMessage().getContentRaw().split(SPACE)).skip(1).collect(joining(SPACE))))
				.mapWith(ApproveAdminResultToStringMapper.INSTANCE);
	}

	private enum ApproveAdminResultToStringMapper implements ApproveAdmin.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = " successfully added to the admins";
		public static final String USER_NOT_FOUND_MESSAGE = " - this nickname doesn't correspond to user";
		public static final String ACCESS_DENIED_MESSAGE = "Sorry, you don't have sufficient rights to perform" +
				" this action";

		@Override
		public String mapSuccess(ApproveAdmin.Result.Success result) {
			return result.getNickname() + SUCCESS_MESSAGE;
		}

		@Override
		public String mapUserNotFoundResult(ApproveAdmin.Result.UserNotFound result) {
			return result.getNickname() + USER_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapAccessDenied(ApproveAdmin.Result.AccessDenied result) {
			return ACCESS_DENIED_MESSAGE;
		}
	}
}
