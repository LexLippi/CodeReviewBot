package com.walle.code.dto.status;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * Компонент, описывающий статус задачи.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public enum SessionStatus {
	CREATED("c"),
	REVIEW("r"),
	ADJUSTMENT("a"),
	FINISHED("f");

	/**
	 * Краткое название статуса
	 */
	@NonNull
	@Getter
	private final String tag;

	public static SessionStatus fromTag(String tag) {
		return Optional.ofNullable(tag)
				.flatMap(nonNullTag -> Arrays.stream(SessionStatus.values())
						.filter(sessionStatus -> sessionStatus.getTag().equals(nonNullTag))
						.findFirst()
				)
				.orElseThrow(() -> new IllegalArgumentException("Status with this tag not found"));
	}
}
