package com.walle.code.adapter.output.row_mapper;

import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.dto.row.ProgrammingLanguageRow;

import javax.persistence.Tuple;

public enum ProgrammingLanguageRowMapper implements RowMapper<ProgrammingLanguageRow> {
	INSTANCE;

	public static final String PARAM_ID = "id";
	public static final String PARAM_C_NAME = "c_name";

	@Override
	public ProgrammingLanguageRow mapRow(Tuple resultSet) {
		return ProgrammingLanguageRow.of(ProgrammingLanguageId.of(resultSet.get(PARAM_ID, Integer.class)),
				resultSet.get(PARAM_C_NAME, String.class));
	}
}
