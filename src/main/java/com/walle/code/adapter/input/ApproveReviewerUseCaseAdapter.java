package com.walle.code.adapter.input;

import com.walle.code.command.ApproveReviewer;
import com.walle.code.dto.row.ReviewerProgrammingLanguageRow;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.input.ApproveReviewerUseCase;
import com.walle.code.port.output.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

import java.util.Arrays;

/**
 * Реализация {@link ApproveReviewerUseCase}.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ApproveReviewerUseCaseAdapter implements ApproveReviewerUseCase {
	public static final String YOU_SUCCESSFULLY_ADDED_TO_REVIEWERS = "You successfully added to reviewers group";
	public static final String ERROR_NAME = "Programming language with this name doesn't exist";
	@NonNull
	private final FindAdminByDiscordUserIdOutputPort findAdminByDiscordUserIdOutputPort;

	@NonNull
	private final FindUserByNicknameOutputPort findUserByNicknameOutputPort;

	@NonNull
	private final InsertReviewerOutputPort insertReviewerOutputPort;

	@NonNull
	private final InsertReviewerProgrammingLanguageOutputPort insertReviewerProgrammingLanguageOutputPort;

	@NonNull
	private final FindProgrammingLanguageByAliasOutputPort findProgrammingLanguageByAliasOutputPort;

	@NonNull
	private final SendMessageOutputPort sendMessageOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@NonNull
	private final FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort;

	@Override
	@NonNull
	public ApproveReviewer.Result approveReviewer(@NonNull ApproveReviewer command) {
		return this.findAdminByDiscordUserIdOutputPort.findAdminByDiscordUserId(command.getDiscordUserId())
				.map(admin -> this.findUserByNicknameOutputPort.findUserByNickname(command.getNickname())
						.map(user -> {
							this.transactionOperations.executeWithoutResult(status -> {
								var reviewerId = this.insertReviewerOutputPort.insertReviewer(ReviewerRow.of(
										null, user.getId()));
								Arrays.stream(command.getProgrammingLanguages())
										.forEach(programmingLanguageName -> this.findProgrammingLanguageByAliasOutputPort
												.findProgrammingLanguageByAlias(programmingLanguageName)
												.ifPresentOrElse(programmingLanguage ->
														this.insertReviewerProgrammingLanguageOutputPort
																.insertReviewerProgrammingLanguage(
																		ReviewerProgrammingLanguageRow.of(null,
																				reviewerId,
																				programmingLanguage.getId())),
														() -> this.sendMessageOutputPort.sendMessage(
																this.findUserByDiscordIdOutputPort.findUserByDiscordId(
																		command.getDiscordUserId()).orElseThrow(),
																ERROR_NAME)));
							});
							this.sendMessageOutputPort.sendMessage(user, YOU_SUCCESSFULLY_ADDED_TO_REVIEWERS);
							return ApproveReviewer.Result.success(user.getNickname());
						})
						.orElse(ApproveReviewer.Result.userNotFound(command.getNickname())))
				.orElse(ApproveReviewer.Result.accessDenied());
	}
}
