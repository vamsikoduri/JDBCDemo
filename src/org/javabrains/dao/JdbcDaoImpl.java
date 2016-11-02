package org.javabrains.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.javabrains.model.Circle;
import org.javabrains.model.Triangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class JdbcDaoImpl {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedTemplate;

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

	public int getCircleCount() {
		String sql = "SELECT COUNT(*) FROM CIRCLE";
		// jdbcTemplate.setDataSource(getDataSource());
		return jdbcTemplate.queryForInt(sql);
	}

	public String getCircleName(int circleId) {

		String sql = "SELECT NAME FROM CIRCLE where id=?";
		return jdbcTemplate.queryForObject(sql, new Object[] { circleId }, String.class);
	}

	public Circle getCircleForId(int circleId) {
		String sql = "SELECT * FROM CIRCLE where ID=?";
		return jdbcTemplate.queryForObject(sql, new Object[] { circleId }, new CircleMapper());

	}

	public Triangle getTriangleForId(int triangleId) {
		String sql = "SELECT * FROM Triangle where ID=?";
		return jdbcTemplate.queryForObject(sql, new Object[] { triangleId }, new TriangleMapper());

	}

	public List<Circle> getAllCircle() {
		String sql = "SELECT * FROM CIRCLE";

		return jdbcTemplate.query(sql, new CircleMapper());

	}

	public void createTriangle() {
		String sql = "CREATE TABLE TRIANGLE (ID INTEGER,NAME VARCHAR(50))";
		jdbcTemplate.update(sql);

	}

	/*
	 * public void insertCircle(Circle circle) { String sql =
	 * "Insert into CIRCLE (ID,NAME) VALUES(?,?)"; jdbcTemplate.update(sql, new
	 * Object[] { circle.getNo(), circle.getName() }); }
	 */

	public void insertCircle(Circle circle) {
		String sql = "Insert into CIRCLE (ID,NAME) VALUES(:id,:name)";
		SqlParameterSource params = new MapSqlParameterSource("id", circle.getNo()).addValue("name", circle.getName());
		namedTemplate.update(sql, params);
	}

	public void insertTrinagle(Triangle triangle) {
		String sql = "Insert into Triangle (ID,NAME) VALUES(?,?)";
		jdbcTemplate.update(sql, new Object[] { triangle.getNo(), triangle.getName() });
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
		this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public class CircleMapper implements RowMapper<Circle> {

		@Override
		public Circle mapRow(ResultSet rs, int rowCount) throws SQLException {

			return new Circle(rs.getInt("ID"), rs.getString("Name"));
		}

	}

	public class TriangleMapper implements RowMapper<Triangle> {

		@Override
		public Triangle mapRow(ResultSet rs, int rowCount) throws SQLException {

			return new Triangle(rs.getInt("ID"), rs.getString("Name"));
		}

	}

}
