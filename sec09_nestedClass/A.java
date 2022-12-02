package sec09_nestedClass;

//외부 클래스
class A {
    A() {
        System.out.println("A 객체가 생성됨");
    }

    //인스턴스 멤버 클래스
    //A 객체를 생성해야 사용할 수 있는 B 중첩 클래스
    class B {
        B() {
            System.out.println("B 객체가 생성됨");
        }
        int field1;
        void method1(){}

        //static 멤버 선언 불가
        //static int field2;
        //static void method2() { }
    }

    //정적 멤버 클래스
    //A 클래스로 바로 접근할 수 있는 B 중첩 클래스
    static class C {
        C() {
            System.out.println("C 객체가 생성됨");
        }
        int field1;
        static int field2;
        void method1() {}
        static void method2() {}
    }

    //method()가 실행할 때만 사용할 수 있는 B중첩 클래스
    void method() {
        //로컬 클래스
        class D {
            D() {
                System.out.println("D 객체가 생성됨");
            }
            int field1;
            //static int field2;
            void method1(){}
            //static void method2(){}
        }
        D d = new D();
        d.field1 = 3;
        d.method1();
    }
}
