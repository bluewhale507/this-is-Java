package sec11_basicAPI.example.Class;

import sec07_inheritance.class_promotion.inheritance_example.Car;

public class ClassExample {
	public static void main(String[] args) {
		Car car = new Car();
		Class clazz1 = car.getClass();
		System.out.println(clazz1.getName());
		//패키지명을 제외한 순수한 클래스이름 반환
		System.out.println(clazz1.getSimpleName());
		System.out.println(clazz1.getPackage());
		System.out.println(clazz1.getPackage().getName());
		System.out.println();
		
		try {
			Class clazz2 = Class.forName("sec07_inheritance.class_promotion.inheritance_example.Car" );
			System.out.println(clazz2.getName());
			System.out.println(clazz2.getSimpleName());
			System.out.println(clazz2.getPackage());
			System.out.println(clazz2.getPackage().getName());
			System.out.println();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
