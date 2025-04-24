package sec14_lambdaExpressions.example.functionalInterfaceOfStandardAPI;

import java.util.function.IntBinaryOperator;

public class OperatorExample {
    private static int[] scores = {92, 95, 87};

    // maxOrMin는 함수적 인터페이스를 인자로 가져, 람다표현식을 인자로 받는다.
    // 람다 표현식으로 생성되는 익명 구현 객체의 추상메서드 applyAsInt 재정의하고 실행한다.
    public static int maxOrMin(IntBinaryOperator operator) {
        int result = scores[0];
        for (int score : scores) {
            result = operator.applyAsInt(result, score);
        }
        return result;
    }

    public static void main(String[] args) {
        int max = maxOrMin(
            (a, b) -> {
                // 추상 메서드 applyAsInt의 구현부를 작성하고 람다를 이용해 인자로 넘김
                if (a >= b) return a;
                else return b;
            });
        System.out.println("최대값: " + max);

        int min = maxOrMin(
            (a, b) -> {
                if(a <= b) return a;
                else return b;
            });
        System.out.println("최소값: " + min);
    }
}
