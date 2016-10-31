package org.javabrains.demo;

import org.javabrains.dao.JdbcDaoImpl;
import org.javabrains.model.Circle;

public class JdbcDemo {

	public static void main(String args[]) {

		Circle circle = new JdbcDaoImpl().getCircle(1);
		if(null != circle)
		{
			System.out.println("name of the circle from db:" + circle.getName());	
		}
		else
		{
			System.out.println("instance of circle is null");
		}
	}

}
