package com.walle.code.adapter.output.row_mapper;

import javax.persistence.Tuple;

/**
 * Компонент для преобразования объектов, полученных из источника данных {@link Tuple} в объекты типа {@link T}
 *
 * @author <a href="mailto:alekseilipatkin@mail.ru">Алексей Липаткин</a>.
 * @since 21.1.0
 */
@FunctionalInterface
public interface RowMapper<T> {
	/**
	 * Метод преобразования объектов, полученных из источника данных {@link Tuple} в объекты типа {@link T}
	 * @param resultSet объект, полученный из источника данных {@link Tuple}
	 * @return объект типа {@link T}
	 */
	T mapRow(Tuple resultSet);
}
