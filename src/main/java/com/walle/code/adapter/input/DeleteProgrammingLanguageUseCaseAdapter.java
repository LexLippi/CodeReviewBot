package com.walle.code.adapter.input;

import com.walle.code.command.DeleteProgrammingLanguage;
import com.walle.code.port.input.DeleteProgrammingLanguageUseCase;
import com.walle.code.port.output.DeleteReviewerProgrammingLanguageOutputPort;
import com.walle.code.port.output.FindProgrammingLanguageByAliasOutputPort;
import com.walle.code.port.output.FindReviewerByDiscordIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

/**
 * Реализация {@link DeleteProgrammingLanguageUseCase}
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class DeleteProgrammingLanguageUseCaseAdapter implements DeleteProgrammingLanguageUseCase {
	@NonNull
	private final FindReviewerByDiscordIdOutputPort findReviewerByDiscordIdOutputPort;

	@NonNull
	private final FindProgrammingLanguageByAliasOutputPort findProgrammingLanguageByAliasOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@NonNull
	private final DeleteReviewerProgrammingLanguageOutputPort deleteReviewerProgrammingLanguageOutputPort;

	@Override
	@NonNull
	public DeleteProgrammingLanguage.Result deleteProgrammingLanguage(@NonNull DeleteProgrammingLanguage command) {
		return this.findReviewerByDiscordIdOutputPort.findReviewerByDiscordId(command.getDiscordUserId())
				.map(reviewer -> this.findProgrammingLanguageByAliasOutputPort.findProgrammingLanguageByAlias(
						command.getProgrammingLanguageAlias())
						.map(programmingLanguage -> {
							this.transactionOperations.executeWithoutResult(status ->
									this.deleteReviewerProgrammingLanguageOutputPort.deleteReviewerProgrammingLanguage(
											reviewer.getId(), programmingLanguage.getId()));
							return DeleteProgrammingLanguage.Result.success();
						})
						.orElse(DeleteProgrammingLanguage.Result.programmingLanguageNotFound()))
				.orElse(DeleteProgrammingLanguage.Result.reviewerNotFound());
	}
}
