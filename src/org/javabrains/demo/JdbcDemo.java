package org.javabrains.demo;

import org.javabrains.dao.JdbcDaoImpl;
import org.javabrains.model.Circle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcDemo {

	public static void main(String args[]) {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

		JdbcDaoImpl impl = ctx.getBean("jdbcDaoImpl", JdbcDaoImpl.class);

		Circle circle = impl.getCircle(1);
		if (null != circle) {
			System.out.println("name of the circle from db:" + circle.getName());
		} else {
			System.out.println("instance of circle is null");
		}
	}

}
