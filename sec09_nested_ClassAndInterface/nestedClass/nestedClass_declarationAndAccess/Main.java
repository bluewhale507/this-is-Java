package sec09_nested_ClassAndInterface.nestedClass.nestedClass_declarationAndAccess;

public class Main {
    public static void main(String[] args) {
        A a = new A();

        //인스턴스 클래스 멤버 객체생성
        A.B b = a.new B();
        b.field1 = 3;
        b.method();

        //정적 멤버 클래스 객체 생성
        A.C c = new A.C();
        c.field1 = 3;
        c.method();
        //정적 메서드는 객체없이 호출가능
        A.C.field2 = 3;


        //로컬 클래스 객체 생성을 위한 메서드 호출 - 메서드 실행시에만 사용 가능
        a.method();
    }
}
