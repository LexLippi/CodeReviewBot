package com.walle.code.adapter.output.row_wrapper;

import com.walle.code.adapter.output.row_mapper.RowMapper;

import javax.persistence.EntityManager;

/**
 * Компонент для преобразования объектов, полученных из конвертации в {@link RowMapper<TRow>}
 * В качестве обёрток выступают данные из БД
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */

@FunctionalInterface
public interface RowWrapper<TRow, TWrap> {
    /**
     * Метод преобразования объектов, полученных из конвертированных данных {@link TRow} в объекты типа {@link TWrap}
     * @param resultSet объект, полученный из конвертации {@link TRow}
     * @return объект типа {@link TWrap}
     */
    TWrap wrapRow(TRow resultSet, EntityManager entityManager);
}
