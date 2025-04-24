package sec14_lambdaExpressions.example.functionalInterfaceOfStandardAPI.defaultMethod;

import java.util.function.Function;

public class FunctionalAndComposeExample {
    public static void main(String[] args) {
        Function<Member, Address> functionA;
        Function<Address, String> functionB;
        Function<Member, String> functionAB;
        String city;

        functionA = m -> m.getAddress();
        functionB = m -> m.getCity();
        functionAB = functionA.andThen(functionB);
        
        // functionAB는 Member을 받아 String으로 반환하는 함수적 인터페이스
        city = functionAB.apply(new Member("홍길동", "hong", new Address("한국", "서울")));
        System.out.println("거주 도시: " + city);

        functionAB = functionB.compose(functionA);
        city = functionAB.apply(new Member("홍길동", "hong", new Address("한국", "서울")));
        System.out.println("거주 도시: " + city);
    }
}
