package com.walle.code.adapter.output.javax.mail;

import com.walle.code.port.output.IsEmailCorrectOutputPort;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Реализация {@link IsEmailCorrectOutputPort} с использованием Javax Mail
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@Slf4j
public enum JavaxMailIsEmailCorrectOutputPort implements IsEmailCorrectOutputPort {
	INSTANCE;

	@Override
	public boolean isEmailCorrect(@NonNull String email) {
		try {
			new InternetAddress(email).validate();
			return true;
		} catch (AddressException addressException) {
			log.error(addressException.getMessage(), addressException);
			return false;
		}
	}
}
