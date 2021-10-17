package com.walle.code.adapter.input;

import com.walle.code.command.ApproveReviewer;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.input.ApproveReviewerUseCase;
import com.walle.code.port.output.FindAdminByDiscordUserIdOutputPort;
import com.walle.code.port.output.FindUserByNicknameOutputPort;
import com.walle.code.port.output.InsertReviewerOutputPort;
import com.walle.code.port.output.SendMessageByDiscordIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

/**
 * Реализация {@link ApproveReviewerUseCase}.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ApproveReviewerUseCaseAdapter implements ApproveReviewerUseCase {
	public static final String YOU_SUCCESSFULLY_ADDED_TO_REVIEWERS = "You successfully added to reviewers group";

	@NonNull
	private final FindAdminByDiscordUserIdOutputPort findAdminByDiscordUserIdOutputPort;

	@NonNull
	private final FindUserByNicknameOutputPort findUserByNicknameOutputPort;

	@NonNull
	private final InsertReviewerOutputPort insertReviewerOutputPort;

	@NonNull
	private final SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@Override
	public ApproveReviewer.Result approveReviewer(ApproveReviewer command) {
		return this.findAdminByDiscordUserIdOutputPort.findAdminByDiscordUserId(command.getDiscordUserId())
				.map(admin -> this.findUserByNicknameOutputPort.findUserByNickname(command.getNickname())
						.map(user -> {
							this.transactionOperations.executeWithoutResult(status -> this.insertReviewerOutputPort
									.insertReviewer(ReviewerRow.of(
											null,
											user.getId())));
							this.sendMessageByDiscordIdOutputPort.sendMessageByDiscordId(user.getDiscordId(),
									YOU_SUCCESSFULLY_ADDED_TO_REVIEWERS);
							return ApproveReviewer.Result.success(user.getNickname());
						})
						.orElse(ApproveReviewer.Result.userNotFound(command.getNickname())))
				.orElse(ApproveReviewer.Result.accessDenied());
	}
}
