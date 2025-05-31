package sec16_streamIterator.example.commonFeatures;

import java.util.Arrays;
import java.util.List;

public class MapAndReduceExample {
    public static void main(String[] args) {
        List<Student> studentList = Arrays.asList(
                new Student("홍길동", 10),
                new Student("신용권", 20),
                new Student("유미선", 30)
        );

        double avg = studentList.stream()
                /* 중간 처리 */
//                .mapToInt(s -> s.getScore())
                .mapToInt(Student :: getScore)
                /* 최종 처리 */
                .average()
                .getAsDouble();

        System.out.println("평균점수: " + avg);
    }
}
