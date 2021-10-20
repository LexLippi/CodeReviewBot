package com.walle.code.handler;

import com.walle.code.command.ApproveReviewer;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.ApproveReviewerUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Компонент для подтверждения регистрации ревьюера.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ApproveReviewerHandler {
	public static final String SPACE = " ";

	@NonNull
	private final ApproveReviewerUseCase approveReviewerUseCase;

	@NonNull
	public String handle(@NonNull MessageReceivedEvent event) {

		return this.approveReviewerUseCase.approveReviewer(ApproveReviewer.of(
				DiscordUserId.of(event.getAuthor().getId()),
				event.getMessage().getContentRaw().split(SPACE)[1]))
				.mapWith(ApproveReviewerResultToStringMapper.INSTANCE);
	}

	private enum ApproveReviewerResultToStringMapper implements ApproveReviewer.Result.Mapper<String> {
		INSTANCE;

		public static final String SUCCESS_MESSAGE = " successfully added to the reviewers";
		public static final String USER_NOT_FOUND_MESSAGE = " - this nickname doesn't correspond to user";
		public static final String ACCESS_DENIED_MESSAGE = "Sorry, you don't have sufficient rights to perform" +
				" this action";

		@Override
		public String mapSuccess(ApproveReviewer.Result.Success result) {
			return result.getNickname() + SUCCESS_MESSAGE;
		}

		@Override
		public String mapUserNotFoundResult(ApproveReviewer.Result.UserNotFound result) {
			return result.getNickname() + USER_NOT_FOUND_MESSAGE;
		}

		@Override
		public String mapAccessDenied(ApproveReviewer.Result.AccessDenied result) {
			return ACCESS_DENIED_MESSAGE;
		}
	}
}
