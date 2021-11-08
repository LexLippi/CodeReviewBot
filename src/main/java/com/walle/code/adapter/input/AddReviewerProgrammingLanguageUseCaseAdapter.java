package com.walle.code.adapter.input;

import com.walle.code.command.AddReviewerProgrammingLanguage;
import com.walle.code.dto.row.AdminRow;
import com.walle.code.port.input.AddReviewerProgrammingLanguageUseCase;
import com.walle.code.port.output.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.toSet;

/**
 * Реализация {@link AddReviewerProgrammingLanguageUseCase}.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class AddReviewerProgrammingLanguageUseCaseAdapter implements AddReviewerProgrammingLanguageUseCase {
	public static final String REVIEWER_WANT = "  - this reviewer want to add new programming language ";
	public static final String CONNECT_WITH_HIM = ". Please, connect with him and approve or ignore his query.";

	@NonNull
	private final FindReviewerByDiscordIdOutputPort findReviewerByDiscordIdOutputPort;

	@NonNull
	private final ReviewerProgrammingLanguageExistOutputPort reviewerProgrammingLanguageExistOutputPort;

	@NonNull
	private final SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort;

	@NonNull
	private final FindAdminsOutputPort findAdminsOutputPort;

	@NonNull
	private final FindUserByIdInOutputPort findUserByIdInOutputPort;

	@Override
	@NonNull
	public AddReviewerProgrammingLanguage.Result addReviewerProgrammingLanguage(
			@NonNull AddReviewerProgrammingLanguage command) {
		return this.findReviewerByDiscordIdOutputPort.findReviewerByDiscordId(command.getDiscordUserId())
						.map(reviewer -> {
							if (this.reviewerProgrammingLanguageExistOutputPort.reviewerProgrammingLanguageExist(
									reviewer.getId(), command.getProgrammingLanguageAlias())) {
								return AddReviewerProgrammingLanguage.Result.reviewerProgrammingLanguageAlreadyExists();
							}
							this.findUserByIdInOutputPort.findUserByIdIn(this.findAdminsOutputPort.findAdmins()
									.stream()
									.map(AdminRow::getUserId)
									.collect(toSet()))
									.forEach(adminUser -> this.sendMessageByDiscordIdOutputPort.sendMessageByDiscordId(
											adminUser.getDiscordId(),
											command.getNickname() + REVIEWER_WANT +
													command.getProgrammingLanguageAlias() + CONNECT_WITH_HIM));
							return AddReviewerProgrammingLanguage.Result.success();
						})
						.orElse(AddReviewerProgrammingLanguage.Result.reviewerNotFound());
	}
}
