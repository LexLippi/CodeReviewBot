package com.walle.code.adapter.input;

import com.walle.code.command.RegisterStudent;
import com.walle.code.dto.row.StudentRow;
import com.walle.code.dto.row.UserRow;
import com.walle.code.port.input.RegisterStudentUseCase;
import com.walle.code.port.output.FindStudentByUserIdOutputPort;
import com.walle.code.port.output.FindUserByDiscordIdOutputPort;
import com.walle.code.port.output.InsertStudentOutputPort;
import com.walle.code.port.output.InsertUserOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

/**
 * Реализация {@link RegisterStudentUseCase}.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class RegisterStudentUseCaseAdapter implements RegisterStudentUseCase {
	@NonNull
	private final FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort;

	@NonNull
	private final FindStudentByUserIdOutputPort findStudentByUserIdOutputPort;

	@NonNull
	private final InsertStudentOutputPort insertStudentOutputPort;

	@NonNull
	private final InsertUserOutputPort insertUserOutputPort;

	@NonNull
	private final TransactionOperations transactionOperations;

	@Override
	@NonNull
	public RegisterStudent.Result registerStudent(@NonNull RegisterStudent command) {
		return this.findUserByDiscordIdOutputPort.findUserByDiscordId(command.getDiscordUserId())
				.map(user -> this.findStudentByUserIdOutputPort.findStudentByUserId(user.getId())
						.map(student -> RegisterStudent.Result.studentAlreadyRegister(student.getId()))
						.orElse(RegisterStudent.Result.success(this.transactionOperations.execute(status ->
								this.insertStudentOutputPort.insertStudent(StudentRow.of(
										null,
										user.getId()))))))
				.orElse(RegisterStudent.Result.success(this.transactionOperations.execute(status ->
						this.insertStudentOutputPort.insertStudent(StudentRow.of(
								null,
								this.insertUserOutputPort.insertUser(UserRow.of(
										null,
										command.getDiscordUserId(),
										command.getNickname(),
										null,
										null,
										null)))))));
	}
}
