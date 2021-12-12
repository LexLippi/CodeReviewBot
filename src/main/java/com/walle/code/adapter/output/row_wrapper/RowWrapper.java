package com.walle.code.adapter.output.row_wrapper;

import com.walle.code.adapter.output.row_mapper.RowMapper;

import javax.persistence.EntityManager;

/**
 * Компонент для преобразования объектов, полученных из конвертации в {@link RowMapper<K>}
 * В качестве обёрток выступают данные из БД
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */

@FunctionalInterface
public interface RowWrapper<K, V> {
    /**
     * Метод преобразования объектов, полученных из конвертированных данных {@link K} в объекты типа {@link V}
     * @param resultSet объект, полученный из конвертации {@link K}
     * @return объект типа {@link V}
     */
    V wrapRow(K resultSet, EntityManager entityManager);
}
