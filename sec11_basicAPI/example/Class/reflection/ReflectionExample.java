package sec11_basicAPI.example.Class.reflection;

import java.lang.reflect.*;

public class ReflectionExample {
	public static void main(String[] args) throws Exception {
		Class clazz = Class.forName("sec11_basicAPI.example.Class.reflection.Car");
		
		System.out.println("[클래스 이름]");
		//패키지 이름을 포함한 전체 이름을 리턴
		System.out.println(clazz.getName());
		System.out.println();
		
		System.out.println("[생성자 정보]");
		//Car클래스의 생성자를 constuctor객체를 만들어 배열로 리턴
		Constructor[] constructors = clazz.getDeclaredConstructors();
		for(Constructor constructor : constructors) {
			System.out.print(constructor.getName() + "(");
			//매개변수를 선언된 순서대로 나타내는 Class객체의 배열로 리턴
			Class[] parameters = constructor.getParameterTypes();
			printParameters(parameters);
			System.out.println(")");
		}
		System.out.println();
		
		System.out.println("[필드 정보]");
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			System.out.println(field.getType().getSimpleName() + " " + field.getName());
		}
		System.out.println();
		
		System.out.println("[메소드 정보]");
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			System.out.print(method.getReturnType().getSimpleName() + " ");
			System.out.print(method.getName() + "(");
			Class[] parameters = method.getParameterTypes();
			printParameters(parameters);
			System.out.println(")");
		}
	}
	
	private static void printParameters(Class[] parameters) {
		for(int i=0; i<parameters.length; i++) {
			System.out.print(parameters[i].getName());
			if(i<(parameters.length-1)) System.out.print(",");
		}
	}
}
