package com.walle.code.adapter.input;

import com.walle.code.command.ApproveReviewerProgrammingLanguage;
import com.walle.code.dto.row.ReviewerProgrammingLanguageRow;
import com.walle.code.port.input.ApproveReviewerProgrammingLanguageUseCase;
import com.walle.code.port.output.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

/**
 * Реализация {@link ApproveReviewerProgrammingLanguageUseCase}
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ApproveReviewerProgrammingLanguageUseCaseAdapter
		implements ApproveReviewerProgrammingLanguageUseCase {
	public static final String REQUEST = "You request for add programming language ";
	public static final String APPROVED = " was approved";

	@NonNull
	private final FindAdminByDiscordUserIdOutputPort findAdminByDiscordUserIdOutputPort;

	@NonNull
	private final FindUserByNicknameOutputPort findUserByNicknameOutputPort;

	@NonNull
	private final FindReviewerByUserIdOutputPort findReviewerByUserIdOutputPort;

	@NonNull
	private final FindProgrammingLanguageByAliasOutputPort findProgrammingLanguageByAliasOutputPort;

	@NonNull
	private final InsertReviewerProgrammingLanguageOutputPort insertReviewerProgrammingLanguageOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@NonNull
	private final SendMessageOutputPort sendMessageOutputPort;

	@Override
	@NonNull
	public ApproveReviewerProgrammingLanguage.Result approveReviewerProgrammingLanguage(
			@NonNull ApproveReviewerProgrammingLanguage command) {
		return this.findAdminByDiscordUserIdOutputPort.findAdminByDiscordUserId(command.getDiscordUserId())
				.map(admin -> this.findUserByNicknameOutputPort.findUserByNickname(command.getNickname())
						.map(reviewerUser -> this.findReviewerByUserIdOutputPort.findReviewerByUserId(reviewerUser
								.getId())
								.map(reviewer -> this.findProgrammingLanguageByAliasOutputPort
										.findProgrammingLanguageByAlias(command.getProgrammingLanguageAlias())
										.map(programmingLanguage -> {
											this.transactionOperations.executeWithoutResult(status ->
													this.insertReviewerProgrammingLanguageOutputPort
															.insertReviewerProgrammingLanguage(
																	ReviewerProgrammingLanguageRow.of(null,
																			reviewer.getId(),
																			programmingLanguage.getId())));
											this.sendMessageOutputPort.sendMessage(reviewerUser,
													REQUEST + programmingLanguage.getName() + APPROVED);
											return ApproveReviewerProgrammingLanguage.Result.success();
										})
										.orElse(ApproveReviewerProgrammingLanguage.Result
												.programmingLanguageNotFound()))
								.orElse(ApproveReviewerProgrammingLanguage.Result.reviewerNotFound()))
						.orElse(ApproveReviewerProgrammingLanguage.Result.reviewerUserNotFound()))
				.orElse(ApproveReviewerProgrammingLanguage.Result.adminNotFound());
	}
}
