package org.javabrains.demo;

import org.javabrains.dao.JdbcDaoImpl;
import org.javabrains.model.Circle;
import org.javabrains.model.Triangle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcDemo {

	public static void main(String args[]) {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

		JdbcDaoImpl impl = ctx.getBean("jdbcDaoImpl", JdbcDaoImpl.class);

		/*
		 * Circle circle = impl.getCircle(1); if (null != circle) {
		 * System.out.println("name of the circle from db:" + circle.getName());
		 * } else { System.out.println("instance of circle is null"); }
		 */

		/*
		 * System.out.println("*** Circle count **** :" +impl.getCircleCount());
		 * System.out.println("*** Circle Name  **** :" +impl.getCircleName(1));
		 */
		/*
		 * Circle circle = impl.getCircleForId(1);
		 * System.out.println(circle.getName());
		 */
		Circle circle = new Circle(5, "Fifth Circle");
		impl.insertCircle(circle);
		System.out.println("Size of the list:" + impl.getAllCircle().size());

		impl.createTriangle();
		impl.insertTrinagle(new Triangle(1, "first Triangle"));
		System.out.println(impl.getTriangleForId(1).getName());
	}

}
