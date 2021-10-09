package com.walle.code.adapter.output.row_mapper;

import com.walle.code.domain.entity.Email;
import com.walle.code.domain.id.DiscordUserId;
import com.walle.code.domain.id.UserId;
import com.walle.code.dto.row.UserRow;

import javax.persistence.Tuple;

public enum UserRowMapper implements RowMapper<UserRow> {
	INSTANCE;

	public static final String PARAM_ID = "id";
	public static final String PARAM_ID_DISCORD = "id_discord";
	public static final String PARAM_C_NICKNAME = "c_nickname";
	public static final String PARAM_C_FIRST_NAME = "c_first_name";
	public static final String PARAM_C_SURNAME = "c_surname";
	public static final String PARAM_C_EMAIL  = "c_email";

	@Override
	public UserRow mapRow(Tuple resultSet) {
		return UserRow.of(UserId.of(resultSet.get(PARAM_ID, Integer.class)),
				DiscordUserId.of(resultSet.get(PARAM_ID_DISCORD, String.class)),
				resultSet.get(PARAM_C_NICKNAME, String.class),
				resultSet.get(PARAM_C_FIRST_NAME, String.class),
				resultSet.get(PARAM_C_SURNAME, String.class),
				Email.of(resultSet.get(PARAM_C_EMAIL, String.class)));
	}
}
