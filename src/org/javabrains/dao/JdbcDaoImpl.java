package org.javabrains.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.javabrains.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdbcDaoImpl {

	@Autowired
	private DataSource dataSource;

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

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
