package sec16_streamIterator.example.Collect;

import java.util.Arrays;
import java.util.List;

public class MaleStudentExample {
    public static void main(String[] args) {
        List<Student$1> totalList = Arrays.asList(
                new Student$1("홍길동", 10, Student$1.Sex.MALE),
                new Student$1("김수애", 6, Student$1.Sex.FEMALE),
                new Student$1("신용권", 10, Student$1.Sex.MALE),
                new Student$1("박수미", 6, Student$1.Sex.FEMALE)
        );

        //순차 처리
        //MaleStudent maleStudent = totalList.stream()

        // 병렬 처리
        MaleStudent maleStudent = totalList.parallelStream()
                .filter(s -> s.getSex() == Student$1.Sex.MALE)
                //.collect(MaleStudent :: new, MaleStudent :: accumulate, MaleStudent :: combine);
                .collect(()->new MaleStudent(), (r, t)->r.accumulate(t), (r1, r2)->r1.combine(r2));

        maleStudent.getList().stream()
                .forEach(s -> System.out.println(s.getName()));
    }
}
