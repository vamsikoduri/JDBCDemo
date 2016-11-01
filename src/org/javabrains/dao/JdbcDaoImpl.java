package org.javabrains.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.javabrains.model.Circle;
import org.springframework.stereotype.Component;

@Component
public class JdbcDaoImpl {

	public Circle getCircle(int circleId) {
		
		/* *************************************************************** 
		
		1.) Create instance of the database driver  by passing the name of the driver.
		2.) Next get the connection(conn) using DriverManager by passing the db connection string
		3.) Using the conn make the prepareStatement by passing the query to be executed.
		4.) substitute the query place holder with the original parameters.
		5.) Get the result set after performing execute query on prepare Statement.
		6.) close the result set and prepare statement;
		7.) close the connection in the finally block so that the connection gets 
			closed even if there is an error.
		 ************************************************************* */

		Circle circle = null;
		Connection conn = null;
		String driver = "org.apache.derby.jdbc.ClientDriver";
		try {
			Class.forName(driver).newInstance(); // 1
			conn = DriverManager.getConnection("jdbc:derby://localhost:1527/db"); // 2
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

}
