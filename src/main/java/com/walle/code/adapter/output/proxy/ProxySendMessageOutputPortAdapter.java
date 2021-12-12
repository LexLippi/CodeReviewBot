package com.walle.code.adapter.output.proxy;

import com.walle.code.dto.row.UserRow;
import com.walle.code.port.output.SendMessageOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Реализация шаблона множественный сток для {@link SendMessageOutputPort}.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class ProxySendMessageOutputPortAdapter implements SendMessageOutputPort {
	@NonNull
	private final List<SendMessageOutputPort> sendMessageOutputPorts;

	@Override
	public void sendMessage(@NonNull UserRow user, @NonNull String text) {
		this.sendMessageOutputPorts.forEach(sendMessageOutputPort -> sendMessageOutputPort.sendMessage(user, text));
	}
}
