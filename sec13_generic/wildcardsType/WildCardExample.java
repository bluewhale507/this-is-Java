package sec13_generic.wildcardsType;

import javax.swing.text.Highlighter;
import java.util.Arrays;

public class WildCardExample {

    // 클래스 구조
    // Person >> Student >> HighStudent
    //        >> Worker

    // 모든 과정
    public static void registerCourse(Course<?> course) {
        System.out.println(course.getName() + " 수강생: " + Arrays.toString(course.getStudents()));
    }

    // 학생 과정
    public static void registerCourseStudent(Course<? extends Student> course) {
        System.out.println(course.getName() + " 수강생: " + Arrays.toString(course.getStudents()));
    }

    // 직장인, 일반인 과정
    public static void registerCourseWorker(Course<? super Worker> course) {
        System.out.println(course.getName() + " 수강생: " + Arrays.toString(course.getStudents()));
    }

    public static void main(String[] args) {
        // 정원 5명의 일반인 과정 강의를 생성 후 수강자 4명 추가
        Course<Person> personCourse = new Course<Person>("일반인 과정", 5);
            personCourse.add(new Person("일반인"));
            personCourse.add(new Worker("직장인"));
            personCourse.add(new Worker("학생"));
            personCourse.add(new Worker("고등학생"));

        // 정원 5명의 직장인 과정 강의를 생성 후 수강자 1명 추가
        Course<Worker> workerCourse = new Course<Worker>("직장인과정",5);
            workerCourse.add(new Worker("직장인"));
            
        // 정원 5명의 학생 과정 생성 후 수강자 2명 추가
        Course<Student> studentCourse = new Course<Student>("학생",5);
            studentCourse.add(new Student("학생"));
            studentCourse.add(new HighStudent("고등학생"));

        // 정원 5명의 고등학생 과정 생성 후 수강자 1명 추가
        Course<HighStudent> highStudentCourse = new Course<HighStudent>("고등학생",5);
            highStudentCourse.add(new HighStudent("고등학생"));

        // 각 타입 파라미터 별 수강생 출력
        registerCourse(personCourse);
        registerCourse(workerCourse);
        registerCourse(studentCourse);
        registerCourse(highStudentCourse);
        System.out.println();

        // Student의 하위 타입 파라미터 수강생만 출력
        registerCourseStudent(studentCourse);
        registerCourseStudent(highStudentCourse);

        // Worker의 상위 타입 파라미터 수강생만 출력
        registerCourseWorker(personCourse);
        registerCourseWorker(workerCourse);
    }
}
