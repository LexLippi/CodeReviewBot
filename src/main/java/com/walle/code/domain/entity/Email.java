package com.walle.code.domain.entity;

import lombok.Value;

/**
 * Компонент, представляющий электронную почту.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Value(staticConstructor = "of")
public final class Email {
	/**
	 * Адрес электронной почты
	 */
	private final String value;
}
