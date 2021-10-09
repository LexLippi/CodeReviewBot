package com.walle.code.adapter.output.row_mapper;

import com.walle.code.domain.id.StudentId;
import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.StudentRow;

import javax.persistence.Tuple;

public enum StudentRowMapper implements RowMapper<StudentRow> {
	INSTANCE;

	public static final String PARAM_ID = "id";
	public static final String PARAM_ID_USER = "id_user";

	@Override
	public StudentRow mapRow(Tuple resultSet) {
		return StudentRow.of(StudentId.of(resultSet.get(PARAM_ID, Integer.class)),
				UserId.of(resultSet.get(PARAM_ID_USER, Integer.class)));
	}
}
