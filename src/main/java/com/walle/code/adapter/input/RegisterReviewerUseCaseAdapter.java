package com.walle.code.adapter.input;

import com.walle.code.command.RegisterReviewer;
import com.walle.code.dto.row.AdminRow;
import com.walle.code.dto.row.StudentRow;
import com.walle.code.dto.row.UserRow;
import com.walle.code.port.input.RegisterReviewerUseCase;
import com.walle.code.port.output.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

/**
 * Реализация {@link RegisterReviewerUseCase}.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class RegisterReviewerUseCaseAdapter implements RegisterReviewerUseCase {
	public static final String THIS_USER = "This user ";
	public static final String WANT_TO_BE_REVIEWER = " want to be reviewer. Please, connect with him";

	@NonNull
	private final FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort;

	@NonNull
	private final FindReviewerByUserIdOutputPort findReviewerByUserIdOutputPort;

	@NonNull
	private final InsertUserOutputPort insertUserOutputPort;

	@NonNull
	private final SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort;

	@NonNull
	private final FindAdminsOutputPort findAdminsOutputPort;

	@NonNull
	private final FindUserByIdInOutputPort findUserByIdInOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@Override
	@NonNull
	public RegisterReviewer.Result registerReviewer(@NonNull RegisterReviewer command) {
		return this.findUserByDiscordIdOutputPort.findUserByDiscordId(command.getDiscordUserId())
				.map(user -> this.findReviewerByUserIdOutputPort.findReviewerByUserId(user.getId())
						.map(reviewer -> RegisterReviewer.Result.reviewerAlreadyRegister(reviewer.getId()))
						.orElseGet(() -> {
							this.findUserByIdInOutputPort.findUserByIdIn(this.findAdminsOutputPort.findAdmins()
									.stream()
									.map(AdminRow::getUserId)
									.collect(toSet()))
									.forEach(adminUser -> this.sendMessageByDiscordIdOutputPort.sendMessageByDiscordId(
											adminUser.getDiscordId(),
											 THIS_USER + command.getNickname() + WANT_TO_BE_REVIEWER));
							return RegisterReviewer.Result.success();
						}))
				.orElseGet(() -> {
					this.transactionOperations.executeWithoutResult(status -> this.insertUserOutputPort.insertUser(
							UserRow.of(null,
									command.getDiscordUserId(),
									command.getNickname(),
									null,
									null,
									null)));
					this.findUserByIdInOutputPort.findUserByIdIn(this.findAdminsOutputPort.findAdmins()
							.stream()
							.map(AdminRow::getUserId)
							.collect(toSet()))
							.forEach(adminUser -> this.sendMessageByDiscordIdOutputPort.sendMessageByDiscordId(
									adminUser.getDiscordId(),
									THIS_USER + command.getNickname() + WANT_TO_BE_REVIEWER));
					return RegisterReviewer.Result.success();
				});
	}
}
