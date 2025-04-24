package sec14_lambdaExpressions.example.methodReference;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ConstructorReferencesExample {
    public static void main(String[] args) {
        Function<String, Member> function1 = Member :: new;
        Member member1 = function1.apply("anger");

        BiFunction<String, String, Member> function2 = Member :: new;
        Member member2 = function2.apply("신천사", "anger");

        /* 매개변수가 없는 생성자는 인자가 없으므로, Function이 아닌 Supplier 인터페이스를 사용해야 한다. */
//        Function function3 = () -> { return new Member(); };
        Supplier<Member> supplier = () -> { return new Member(); };
        Member meber3 = supplier.get();
    }
}
