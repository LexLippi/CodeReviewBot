package com.walle.code.adapter.input;

import com.walle.code.command.CreateSession;
import com.walle.code.dto.row.SessionRow;
import com.walle.code.dto.row.TaskRow;
import com.walle.code.dto.status.SessionStatus;
import com.walle.code.dto.status.TaskStatus;
import com.walle.code.port.input.CreateSessionUseCase;
import com.walle.code.port.output.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Реализация {@link CreateSessionUseCase}.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class CreateSessionUseCaseAdapter implements CreateSessionUseCase {
	@NonNull
	private final FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort;

	@NonNull
	private final FindStudentByUserIdOutputPort findStudentByUserIdOutputPort;

	@NonNull
	private final FindProgrammingLanguageByNameOutputPort findProgrammingLanguageByNameOutputPort;

	@NonNull
	private final FindReviewerOutputPort findReviewerOutputPort;

	@NonNull
	private final InsertSessionOutputPort insertSessionOutputPort;

	@NonNull
	private final InsertTaskOutputPort insertTaskOutputPort;

	@Override
	public CreateSession.Result createSessionUseCase(@NonNull CreateSession command) {
		return this.findUserByDiscordIdOutputPort.findUserByDiscordId(command.getDiscordUserId())
				.map(user -> this.findStudentByUserIdOutputPort.findStudentByUserId(user.getId())
						.map(student -> this.findProgrammingLanguageByNameOutputPort.findProgrammingLanguageByName(
								command.getProgrammingLanguageName())
								.map(programmingLanguage -> this.findReviewerOutputPort.findReviewer(programmingLanguage
										.getId())
										.map(reviewer -> CreateSession.Result.success(this.insertTaskOutputPort
												.insertTask(TaskRow.of(null,
														this.insertSessionOutputPort.insertSession(SessionRow.of(
																null,
																reviewer.getId(),
																student.getId(),
																programmingLanguage.getId(),
																SessionStatus.REVIEW)),
														command.getCodeText(),
														null,
														TaskStatus.CREATED))))
										.orElse(CreateSession.Result.reviewerNotFound()))
								.orElse(CreateSession.Result.programmingLanguageNotFound()))
						.orElse(CreateSession.Result.studentNotFound()))
				.orElse(CreateSession.Result.userNotFound());
	}
}
