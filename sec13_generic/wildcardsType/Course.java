package sec13_generic.wildcardsType;

public class Course<T> {
    private String name;
    private T[] students;   // T는 수강생의 유형, students는 한 Course 당 수강가능한 인원수

    public Course(String name, int capacity) {
        this.name = name;
        students = (T[]) (new Object[capacity]);    // typeParameter로 배열을 생성하려면 new T[n] 형태로 배열을 생성할 수 없다.
    }

    public String getName() { return name; }
    public T[] getStudents() { return students; }
    
    // 수강신청 학생을 빈 공간에 채움, 신청 후 취소할 경우 중간인덱스가 비기 때문에 차례로 삽입
    public void add(T t) {
        for(int i=0; i<students.length; i++) {
            if(students[i] == null) {
                students[i] = t;
                break;
            }
        }
    }
}
