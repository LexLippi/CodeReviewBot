package com.walle.code.handler;

import com.walle.code.command.ApproveReviewer;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.port.input.ApproveReviewerUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

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
		var lines = event.getMessage().getContentRaw().lines().collect(toList());
		if (lines.size() < 2) {
			throw new IllegalArgumentException();
		}
		return this.approveReviewerUseCase.approveReviewer(ApproveReviewer.of(
				DiscordUserId.of(event.getAuthor().getId()),
				Arrays.stream(lines.get(0).split(SPACE)).skip(1).collect(joining(SPACE)),
				lines.get(1).split(SPACE)))
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
