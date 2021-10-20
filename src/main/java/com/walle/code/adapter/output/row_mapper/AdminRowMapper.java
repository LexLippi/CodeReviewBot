package com.walle.code.adapter.output.row_mapper;

import com.walle.code.domain.id.AdminId;
import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.AdminRow;
import lombok.NonNull;

import javax.persistence.Tuple;

public enum AdminRowMapper implements RowMapper<AdminRow> {
	INSTANCE;

	public static final String PARAM_ID = "id";
	public static final String PARAM_ID_USER = "id_user";

	@Override
	@NonNull
	public AdminRow mapRow(@NonNull Tuple resultSet) {
		return AdminRow.of(AdminId.of(resultSet.get(PARAM_ID, Integer.class)),
				UserId.of(resultSet.get(PARAM_ID_USER, Integer.class)));
	}
}
