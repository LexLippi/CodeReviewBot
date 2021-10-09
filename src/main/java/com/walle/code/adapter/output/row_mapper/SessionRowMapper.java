package com.walle.code.adapter.output.row_mapper;


import com.walle.code.domain.id.ProgrammingLanguageId;
import com.walle.code.domain.id.ReviewerId;
import com.walle.code.domain.id.SessionId;
import com.walle.code.domain.id.StudentId;
import com.walle.code.dto.row.SessionRow;
import com.walle.code.dto.status.SessionStatus;

import javax.persistence.Tuple;

public enum SessionRowMapper implements RowMapper<SessionRow> {
	INSTANCE;

	public static final String PARAM_ID = "id";
	public static final String PARAM_ID_REVIEWER = "id_reviewer";
	public static final String PARAM_ID_STUDENT = "id_student";
	public static final String PARAM_ID_PROGRAMMING_LANGUAGE = "id_programming_language";
	public static final String PARAM_C_STATUS = "c_status";

	@Override
	public SessionRow mapRow(Tuple resultSet) {
		return SessionRow.of(SessionId.of(resultSet.get(PARAM_ID, Integer.class)),
				ReviewerId.of(resultSet.get(PARAM_ID_REVIEWER, Integer.class)),
				StudentId.of(resultSet.get(PARAM_ID_STUDENT, Integer.class)),
				ProgrammingLanguageId.of(resultSet.get(PARAM_ID_PROGRAMMING_LANGUAGE, Integer.class)),
				SessionStatus.fromTag(resultSet.get(PARAM_C_STATUS, String.class)));
	}
}
