package sec09_nested_ClassAndInterface.nestedClass.localClass_Access;

//로컬 클래스(매개변수안에서 생성된 클래스)의 외부 클래스 멤버 접근방법
public class Outter {
    //자바 7 이전
    public void method1(final int arg) {
        //final 키워드를 붙인경우에만 로컬클래스에서 접근가능.
        final int localVariable = 1;
        //arg = 100;
        //localVariable = 100;
        class Inner {
            public void mehtod() {
                int result = arg + localVariable;
            }
        }
    }

    //자바 8 이후
    public void method2(int arg) {
        //final 키워드를 안붙여도 접근가능 but final 취급함
        int localVariable = 1;
        //arg = 100;
        //localVariable = 100;
        class Inner {
            public void method() {
                int result = arg + localVariable;
            }
        }
    }
}
