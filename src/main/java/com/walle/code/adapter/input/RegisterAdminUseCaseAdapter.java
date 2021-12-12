package com.walle.code.adapter.input;

import com.walle.code.command.RegisterAdmin;
import com.walle.code.dto.row.AdminRow;
import com.walle.code.dto.row.UserRow;
import com.walle.code.port.input.RegisterAdminUseCase;
import com.walle.code.port.output.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

import static java.util.stream.Collectors.toSet;

/**
 * Реализация {@link RegisterAdminUseCase}.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class RegisterAdminUseCaseAdapter implements RegisterAdminUseCase {
	public static final String THIS_USER = "This user ";
	public static final String WANT_TO_BE_ADMIN = " want to be admin. Please, connect with him!";

	@NonNull
	private final FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort;

	@NonNull
	private final FindAdminByDiscordUserIdOutputPort findAdminByDiscordUserIdOutputPort;

	@NonNull
	private final InsertUserOutputPort insertUserOutputPort;

	@NonNull
	private final SendMessageOutputPort sendMessageOutputPort;

	@NonNull
	private final FindAdminsOutputPort findAdminsOutputPort;

	@NonNull
	private final FindUserByIdInOutputPort findUserByIdInOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@Override
	@NonNull
	public RegisterAdmin.Result registerAdmin(@NonNull RegisterAdmin command) {
		return this.findUserByDiscordIdOutputPort.findUserByDiscordId(command.getDiscordUserId())
				.map(user -> this.findAdminByDiscordUserIdOutputPort.findAdminByDiscordUserId(command
						.getDiscordUserId())
						.map(admin -> RegisterAdmin.Result.adminAlreadyRegister(admin.getId()))
						.orElseGet(() -> {
							this.findUserByIdInOutputPort.findUserByIdIn(this.findAdminsOutputPort.findAdmins()
									.stream()
									.map(AdminRow::getUserId)
									.collect(toSet()))
									.forEach(adminUser -> this.sendMessageOutputPort.sendMessage(adminUser,
											THIS_USER + command.getNickname() + WANT_TO_BE_ADMIN));
							return RegisterAdmin.Result.success();
						}))
				.orElseGet(() -> {
					this.transactionOperations.executeWithoutResult(status -> this.insertUserOutputPort.insertUser(
							UserRow.of(null,
									command.getDiscordUserId(),
									command.getNickname(),
									null,
									null,
									null,
									null)));
					this.findUserByIdInOutputPort.findUserByIdIn(this.findAdminsOutputPort.findAdmins()
							.stream()
							.map(AdminRow::getUserId)
							.collect(toSet()))
							.forEach(adminUser -> this.sendMessageOutputPort.sendMessage(adminUser,
									THIS_USER + command.getNickname() + WANT_TO_BE_ADMIN));
					return RegisterAdmin.Result.success();
				});
	}
}
