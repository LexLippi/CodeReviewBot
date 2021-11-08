package com.walle.code.port.output;

import com.walle.code.domain.id.ReviewerId;

/**
 * Компонент для проверки есть ли в источник данных связь между данным ревьюером и языком программирования.
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface ReviewerProgrammingLanguageExistOutputPort {
	/**
	 * Метод проверки есть ли в источник данных связь между данным ревьюером и языком программирования.
	 *
	 * @param reviewerId               идентификатор ревьюера
	 * @param programmingLanguageAlias сокращение языка программирования
	 * @return ответ есть в источнике данных связь или нет
	 */
	boolean reviewerProgrammingLanguageExist(ReviewerId reviewerId, String programmingLanguageAlias);
}
