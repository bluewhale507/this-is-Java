package sec11_basicAPI.example.newInstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NewInstanceExample {
	public static void main(String[] args) {
		try {
			Class clazz = Class.forName("sec11_basicAPI.example.newInstance.SendAction");
			Action action = (Action)clazz.newInstance();

			Constructor constructor = clazz.getConstructor(String.class);
			Action action2 = (Action) constructor.newInstance("AnotherAction");

			action.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();			// getConstructor()로 인한 예외를 처리
		} catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
