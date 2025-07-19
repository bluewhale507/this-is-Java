package sec16_streamIterator.example.Collect;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupingByExample {
    public static void main(String[] args) {
        List<Student$1> totalList = Arrays.asList(
                new Student$1("홍길동", 10, Student$1.Sex.MALE, Student$1.City.Seoul),
                new Student$1("김수애", 6, Student$1.Sex.FEMALE, Student$1.City.Pusan),
                new Student$1("신용권", 10, Student$1.Sex.MALE, Student$1.City.Pusan),
                new Student$1("박수미", 6, Student$1.Sex.FEMALE, Student$1.City.Seoul)
        );

        Map<Student$1.Sex, List<Student$1>> mapBySex = totalList.stream()
                .collect(Collectors.groupingBy(Student$1 :: getSex));
        System.out.print("[남학생] ");
        mapBySex.get(Student$1.Sex.MALE).stream().forEach(s->System.out.print(s.getName() + " "));
        System.out.print("\n[여학생] ");
        mapBySex.get(Student$1.Sex.FEMALE).stream().forEach(s->System.out.print(s.getName() + " "));

        System.out.println();

        Map<Student$1.City, List<String>> mapByCity = totalList.stream()
                .collect(
                        Collectors.groupingBy(
                                Student$1::getCity,
                                Collectors.mapping(Student$1::getName, Collectors.toList())
                        )
                );
        System.out.print("\n[서울] ");
        mapByCity.get(Student$1.City.Seoul).stream().forEach(s->System.out.print(s + " "));
        System.out.print("\n[부산] ");
        mapByCity.get(Student$1.City.Pusan).stream().forEach(s->System.out.print(s + " "));
    }
}
