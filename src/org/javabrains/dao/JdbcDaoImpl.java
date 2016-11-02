package org.javabrains.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.javabrains.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcDaoImpl {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	public Circle getCircle(int circleId) {

		Circle circle = null;
		Connection conn = null;
		try {

			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select * FROM Circle where id=?"); // 3
			ps.setInt(1, circleId);// 4
			ResultSet rs = ps.executeQuery();// 5
			if (rs.next()) {
				circle = new Circle(circleId, rs.getString("name"));
			}
			rs.close(); // 6
			ps.close(); // 6

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				if (conn != null)
					conn.close(); // 7
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return circle;
	}

	public int getCircleCount()
	{
		String sql = "SELECT COUNT(*) FROM CIRCLE";
		//jdbcTemplate.setDataSource(getDataSource());
		return jdbcTemplate.queryForInt(sql);
	}
	
	
	public String getCircleName(int circleId)
	{
		
		String sql = "SELECT NAME FROM CIRCLE where id=?";
		return jdbcTemplate.queryForObject(sql, new Object[]{circleId}, String.class);
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
