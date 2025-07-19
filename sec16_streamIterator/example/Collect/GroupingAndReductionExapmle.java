package sec16_streamIterator.example.Collect;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupingAndReductionExapmle {
    public class GroupingAndReductionExample {
        public static void main(String[] args) {
            List<Student$1> totalList = Arrays.asList(
                    new Student$1("홍길동", 10, Student$1.Sex.MALE),
                    new Student$1("김수애", 12, Student$1.Sex.FEMALE),
                    new Student$1("신용권", 10, Student$1.Sex.MALE),
                    new Student$1("박수미", 12, Student$1.Sex.FEMALE)
            );

            //성별로 평균 점수를 저장하는 Map 얻기
            Stream<Student$1> totalStream = totalList.stream();
            Function<Student$1, Student$1.Sex> classifier = Student$1 :: getSex;
            ToDoubleFunction<Student$1> mapper = Student$1 :: getScore;
            Collector<Student$1, ?, Double> collector1 = Collectors.averagingDouble(mapper);
            Collector<Student$1, ?, Map<Student$1.Sex, Double>> collector2 = Collectors.groupingBy(classifier, collector1);
            Map<Student$1.Sex, Double> mapBySex = totalStream.collect(collector2);

		/*Map<Student$1.Sex, Double> mapBySex = totalList.stream()
				.collect(
					Collectors.groupingBy(
						Student$1 :: getSex,
						Collectors.averagingDouble(Student$1 :: getScore)
					)
				);*/

            System.out.println("남학생 평균 점수: " + mapBySex.get(Student$1.Sex.MALE));
            System.out.println("여학생 평균 점수: " + mapBySex.get(Student$1.Sex.FEMALE));

            //성별로 쉼표로 구분된 이름을 저장하는 Map 얻기
            Map<Student$1.Sex, String> mapByName = totalList.stream()
                    .collect(
                            Collectors.groupingBy(
                                    Student$1 :: getSex,
                                    Collectors.mapping(
                                            Student$1 :: getName,
                                            Collectors.joining(",")
                                    )
                            )
                    );
            System.out.println("남학생 전체 이름: " + mapByName.get(Student$1.Sex.MALE));
            System.out.println("여학생 전체 이름: " + mapByName.get(Student$1.Sex.FEMALE));
        }
    }

}
