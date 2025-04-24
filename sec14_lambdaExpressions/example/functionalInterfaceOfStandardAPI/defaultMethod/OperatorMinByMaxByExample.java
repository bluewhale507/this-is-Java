package sec14_lambdaExpressions.example.functionalInterfaceOfStandardAPI.defaultMethod;

import java.util.function.BinaryOperator;

public class OperatorMinByMaxByExample {
    public static void main(String[] args) {
        BinaryOperator<Fruit> binaryOperator;
        Fruit fruit;

        // 실제 비교는 minBy의 인자로 받는 Comparator<T>가 수행하고, BinaryOperator<T>는 이를 활용하여 두 개의 값 중 작은 값을 반환하는 역할을 하게 된다.
        binaryOperator = BinaryOperator.minBy( (f1, f2) -> Integer.compare(f1.getPrice(), f2.getPrice()) );
        fruit = binaryOperator.apply(new Fruit("딸기", 6000), new Fruit("수박", 10000));
        System.out.println(fruit.getName());

        binaryOperator = BinaryOperator.maxBy( (f1, f2) -> Integer.compare(f1.getPrice(), f2.getPrice()) );
        fruit = binaryOperator.apply(new Fruit("딸기", 6000), new Fruit("수박", 10000));
        System.out.println(fruit.getName());
    }
}
