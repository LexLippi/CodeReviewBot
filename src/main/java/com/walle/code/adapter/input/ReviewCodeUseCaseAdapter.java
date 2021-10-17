package com.walle.code.adapter.input;

import com.walle.code.command.ReviewCode;
import com.walle.code.dto.status.TaskStatus;
import com.walle.code.port.input.ReviewCodeUseCase;
import com.walle.code.port.output.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

/**
 * Реализация {@link ReviewCodeUseCase}
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ReviewCodeUseCaseAdapter implements ReviewCodeUseCase {
	@NonNull
	private final FindUserByNicknameOutputPort findUserByNicknameOutputPort;

	@NonNull
	private final FindStudentByUserIdOutputPort findStudentByUserIdOutputPort;

	@NonNull
	private final FindReviewerByDiscordIdOutputPort findReviewerByDiscordIdOutputPort;

	@NonNull
	private final FindReviewSessionByStudentIdAndReviewerIdOutputPort
			findReviewSessionByStudentIdAndReviewerIdOutputPort;

	@NonNull
	private final UpdateCreatedTaskBySessionIdOutputPort updateCreatedTaskBySessionIdOutputPort;

	@NonNull
	private final FinishAdjustmentSessionOutputPort finishAdjustmentSessionOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@NonNull
	private final SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort;

	@Override
	public ReviewCode.Result reviewCode(ReviewCode command) {
		return this.findUserByNicknameOutputPort.findUserByNickname(command.getNickname())
				.map(user -> this.findStudentByUserIdOutputPort.findStudentByUserId(user.getId())
						.map(student -> this.findReviewerByDiscordIdOutputPort.findReviewerByDiscordId(
								command.getDiscordUserId())
								.map(reviewer -> this.findReviewSessionByStudentIdAndReviewerIdOutputPort
										.findReviewSessionByStudentIdAndReviewerId(student.getId(),
												reviewer.getId())
										.map(session -> {
											this.transactionOperations.executeWithoutResult(status -> {
												this.updateCreatedTaskBySessionIdOutputPort
														.updateAdjustmentTaskBySessionId(session.getId(),
																command.getText(), command.getStatus());
												if (command.getStatus().equals(TaskStatus.FINISHED)) {
													this.finishAdjustmentSessionOutputPort.finishAdjustmentSession(
															session.getId());
												}
											});
											this.sendMessageByDiscordIdOutputPort.sendMessageByDiscordId(
													user.getDiscordId(), command.getText());
											return ReviewCode.Result.success();
										})
										.orElse(ReviewCode.Result.sessionNotFound()))
								.orElse(ReviewCode.Result.reviewerNotFound()))
						.orElse(ReviewCode.Result.studentNotFound()))
				.orElse(ReviewCode.Result.userNotFound());
	}
}
