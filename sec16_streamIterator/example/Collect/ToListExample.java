package sec16_streamIterator.example.Collect;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ToListExample {
    public static void main(String[] args) {
        List<Student$1> totalList = Arrays.asList(
                new Student$1("홍길동", 10, Student$1.Sex.MALE),
                new Student$1("김수애", 6, Student$1.Sex.FEMALE),
                new Student$1("신용권", 10,Student$1.Sex.MALE),
                new Student$1("박수미", 6,Student$1.Sex.FEMALE)
        );

        // 남학생들만 묶어 List 생성
        List<Student$1> maleList = totalList.stream()
                .filter(s->s.getSex()==Student$1.Sex.MALE)
                .collect(Collectors.toList());
        maleList.stream()
                .forEach(s-> System.out.println(s.getName()));

        System.out.println();

        // 여학생들만 묶어 HashSet 생성
        Set<Student$1> femaleSet = totalList.stream()
                .filter(s->s.getSex()==Student$1.Sex.FEMALE)
                .collect(Collectors.toCollection(HashSet:: new));
        femaleSet.stream()
                .forEach(s-> System.out.println(s.getName()));
    }
}

class Student$1 {
    public enum Sex { MALE, FEMALE }
    public enum City {Seoul, Pusan }

    private String name;
    private int score;
    private Sex sex;
    private City city;

    public Student$1(String name, int score, Sex sex) {
        this.name = name;
        this.score = score;
        this.sex = sex;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public Sex getSex() { return sex; }
    public City getCity() { return city; }
}