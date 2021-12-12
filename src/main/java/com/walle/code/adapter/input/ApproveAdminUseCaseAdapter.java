package com.walle.code.adapter.input;

import com.walle.code.command.ApproveAdmin;
import com.walle.code.dto.row.AdminRow;
import com.walle.code.dto.row.ReviewerRow;
import com.walle.code.port.input.ApproveAdminUseCase;
import com.walle.code.port.output.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

/**
 * Реализация {@link ApproveAdminUseCase}.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ApproveAdminUseCaseAdapter implements ApproveAdminUseCase {
	public static final String YOU_SUCCESSFULLY_ADDED_TO_ADMINS = "You successfully added to admins group";
	@NonNull
	private final FindAdminByDiscordUserIdOutputPort findAdminByDiscordUserIdOutputPort;

	@NonNull
	private final FindUserByNicknameOutputPort findUserByNicknameOutputPort;

	@NonNull
	private final InsertAdminOutputPort insertAdminOutputPort;

	@NonNull
	private final SendMessageOutputPort sendMessageOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@Override
	@NonNull
	public ApproveAdmin.Result approveAdmin(@NonNull ApproveAdmin command) {
		return this.findAdminByDiscordUserIdOutputPort.findAdminByDiscordUserId(command.getDiscordUserId())
				.map(admin -> this.findUserByNicknameOutputPort.findUserByNickname(command.getNickname())
						.map(user -> {
							this.transactionOperations.executeWithoutResult(status -> this.insertAdminOutputPort
									.insertAdmin(AdminRow.of(null, user.getId())));
							this.sendMessageOutputPort.sendMessage(user, YOU_SUCCESSFULLY_ADDED_TO_ADMINS);
							return ApproveAdmin.Result.success(user.getNickname());
						})
						.orElse(ApproveAdmin.Result.userNotFound(command.getNickname())))
				.orElse(ApproveAdmin.Result.accessDenied());
	}
}
