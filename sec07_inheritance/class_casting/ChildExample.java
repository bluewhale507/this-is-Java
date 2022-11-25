package sec07_inheritance.class_casting;

public class ChildExample {
    public static void main(String[] args) {
        Parent parent = new Child();
        parent.field1 = "data";
        parent.method1();
        parent.method2();

        /*
        불가능
        parent.field2 = "data2";
        parent.method3();
        */

        //자식이 부모타입으로 변환되어 있는 상태에서만 가능.
        Child child = (Child) parent;
        child.field2 = "yyy";
        child.method2();
    }
}