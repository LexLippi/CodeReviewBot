package com.walle.code.adapter.output.row_mapper;

import com.walle.code.domain.id.ReviewerId;
import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.ReviewerRow;
import lombok.NonNull;

import javax.persistence.Tuple;

public enum ReviewerRowMapper implements RowMapper<ReviewerRow> {
	INSTANCE;

	public static final String PARAM_ID = "id";
	public static final String PARAM_ID_USER = "id_user";

	@Override
	@NonNull
	public ReviewerRow mapRow(@NonNull Tuple resultSet) {
		return ReviewerRow.of(ReviewerId.of(resultSet.get(PARAM_ID, Integer.class)),
				UserId.of(resultSet.get(PARAM_ID_USER, Integer.class)));
	}
}
