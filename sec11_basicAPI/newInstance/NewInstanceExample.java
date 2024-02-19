package sec11_basicAPI.newInstance;

public class NewInstanceExample {
	public static void main(String[] args) {
		try {
			Class clazz = Class.forName("sec11_basicAPI.newInstance.SendAction");
			Action action = (Action)clazz.newInstance();
			action.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
