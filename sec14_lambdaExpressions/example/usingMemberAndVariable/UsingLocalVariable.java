package sec14_lambdaExpressions.example.usingMemberAndVariable;

import sec14_lambdaExpressions.example.basicSyntax.MyFunctionalInterface;

public class UsingLocalVariable {
    void method(int arg) {  // arg는 final 특성을 가짐
        int localVar = 40;  // localVar는 final 특성을 가짐

        // 수정하는경우 effectively final이 깨져서 로컬 클래스, 람다 내부에서 사용 불가
//        arg = 31;
//        localVar = 41;

        MyFunctionalInterface fi = () -> {
            System.out.println("arg: " + arg);
            System.out.println("localVar: " + localVar + "\n");
        };
        fi.method();
    }
}
