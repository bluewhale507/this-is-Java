package sec14_lambdaExpressions.example.usingMemberAndVariable;

import sec14_lambdaExpressions.example.basicSyntax.MyFunctionalInterface;

public class UsingThis {
    public int outterField = 10;

    class Inner {
        int innerField = 20;

        // lambda 익명 객체 내부에서 this는  MyFunctionalInterface가 아닌, lambda식을 실행한 객체의 참조이다.
        // 여기서 lambda식을 실행한 객체는 Inner 클래스의 인스턴스이다.
        void method() {
            MyFunctionalInterface fi = () -> {
                System.out.println("outterField: " + outterField);
                System.out.println("outterField: " + UsingThis.this.outterField + "\n");

                System.out.println("innerField: " + innerField);
                System.out.println("innerField: " + this.innerField + "\n");
            };
            fi.method();
        }
    }
}
