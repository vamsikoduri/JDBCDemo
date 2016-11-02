package org.javabrains.dao;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class SimpleJdbcDaoImpl extends SimpleJdbcDaoSupport {

	public int getCircelCount() {
		String sql = "Select count(*) from Circle";
		return this.getJdbcTemplate().queryForInt(sql);
	}

}
