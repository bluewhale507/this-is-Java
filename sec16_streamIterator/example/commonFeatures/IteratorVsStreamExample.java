package sec16_streamIterator.example.commonFeatures;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class IteratorVsStreamExample {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("홍길동", "신용권", "김자바");

        //Iterator 이용
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println();

        //Stream 이용
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);    // 람다 표현식의 메서드 참조 기능
    }
}
