package com.walle.code.router;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Компонент для доставки события в специализированный обработчик.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface EventRouter {
	/**
	 * Метод доставки события в специализированный обработчик.
	 *
	 * @param event событие
	 * @return Результат обработки события - строка с ответом
	 */
	String routingEvent(MessageReceivedEvent event);
}
