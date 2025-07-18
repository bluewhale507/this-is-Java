package sec16_streamIterator.example.filtering;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class SotrtingExample {
    public static void main(String[] args) {
        //숫자 요소일 경우
        IntStream intStream = Arrays.stream(new int[] {5,3,2,1,4});
        intStream.sorted().
                forEach(n -> System.out.println(n + ", "));
        
        System.out.println();

        //객체 요소일 경우
        List<Student$2> studentList = Arrays.asList(
                new Student$2("홍길동", 10),
                new Student$2("신용권", 20),
                new Student$2("조자룡", 30)
        );

        studentList.stream()
                .sorted()
                .forEach(s -> System.out.println(s.getScore() + ", "));

        System.out.println();

        studentList.stream()
                .sorted( Comparator.reverseOrder() )
                .forEach(s-> System.out.println(s.getScore() + ", "));
    }
}

class Student$2 implements Comparable<Student$2> {
    private String name;
    private int score;

    public Student$2(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    @Override
    public int compareTo(Student$2 o) {
        return Integer.compare(score, o.getScore());
    }
}
