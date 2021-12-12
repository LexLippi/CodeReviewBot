package com.walle.code.adapter.output.javax.mail;

import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.SendMessageOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Реализация {@link SendMessageOutputPort} с использованием Javax Mail.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
@Slf4j
public final class JavaxMailSendMessageOutputPortAdapter implements SendMessageOutputPort {
	public static final String ANY_ADDRESS = "*";
	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String MAIL_SMTP_PORT = "mail.smtp.port";
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTPS_CHECKSERVERIDENTITY = "mail.smtps.ssl.checkserveridentity";
	public static final String MAIL_SMTPS_SSL_TRUST = "mail.smtps.ssl.trust";
	public static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
	public static final String EMAIL_SUBJECT = "Notification from wall-e Discord Code Review Bot";

	@NonNull
	private final InternetAddress email;

	@NonNull
	private final String password;

	@NonNull
	private final String smtpHost;

	@NonNull
	private final int smtpPort;

	@Override
	public void sendMessage(@NonNull UserRow user, @NonNull String text) {
		if (user.getEmail() == null) {
			return;
		}

		try {
			var properties = new Properties();
			properties.put(MAIL_SMTP_AUTH, true);
			properties.put(MAIL_SMTP_HOST, this.smtpHost);
			properties.put(MAIL_SMTP_PORT, this.smtpPort);
			properties.put(MAIL_SMTP_STARTTLS_ENABLE, true);
			properties.put(MAIL_SMTPS_CHECKSERVERIDENTITY, true);
			properties.put(MAIL_SMTPS_SSL_TRUST, ANY_ADDRESS);
			properties.put(MAIL_SMTP_SSL_ENABLE, true);
			var message = new MimeMessage(Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email.getAddress(), password);
				}
			}));
			message.setFrom(this.email);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail().getValue()));
			message.setSubject(EMAIL_SUBJECT);
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException e) {
			log.error(e.getMessage(), e);
		}

	}
}
