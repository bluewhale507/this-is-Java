package sec09_nested_ClassAndInterface.nestedClass.nestedClass_declarationAndAccess;

/* 외부 클래스 */
class A {
    A() {
        System.out.println("A 객체가 생성됨");
    }

    int fieldA;
    static int fieldB;

    B field1 = new B();
    C field2 = new C();
    //static B field3 = new B(); - static 필드에 대입 불가능(B객체는 A객체가 선언되어야 쓸수 있는반면, static필드는 객체생성없이 사용가능 하므로)
    static C field4 = new C();

    void method1() {
        B var1 = new B();
        C var2 = new C();
    }
    static void method2() {
        //B var1 = new B();
        C var2 = new C();
    }

    /* 인스턴스 멤버 클래스 : 객체를 생성해야 사용할 수 있는 B 중첩 클래스 */
    class B {
        B() {
            System.out.println("B 객체가 생성됨");
        }
        int field1;

        void method(){
            //인스턴스 멤버 클래스에서는 바깥 클래스의 모든 필드와 메서드에 접근가능
            fieldA= 10;
            method1();

        }

        //static 멤버 선언 불가 - 인스턴스멤버클래스는 바깥클래스객체가 생성되어야 사용가능하지만, static 멤버는 객체없이 호출될 수 있기때문
        //static int field2;
        //static void method2() { }
    }

    /* 정적 멤버 클래스 : A 클래스로 바로 접근할 수 있는 B 중첩 클래스 */
    static class C {
        C() {
            System.out.println("C 객체가 생성됨");
        }
        int field1;
        static int field2;
        void method() {
            //static 멤버 클래스에서는 인스턴스 필드와 메서드는 접근 불가능
            //fieldA = 10;
            //method1();
            fieldB = 10;
            method2();
        }
        static void method2() { }
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
