package sec10_exception.example.Exception_throws;

public class ThrowExample {
    public static void main(String[] args) {
        try {
            findClass();
        } catch(ClassNotFoundException e) {
            System.out.println("클래스가 존재하지 않습니다.");
        }
    }

    public static void findClass() throws ClassNotFoundException {
        //Class.forName - Class의 이름을 받아서 객체를 반환함.
        Class clazz = Class.forName("java.lang.String2");
    }
}
