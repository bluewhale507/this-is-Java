package sec15_JCF.example.mapCollection;

import java.util.HashMap;
import java.util.Map;

public class HashMapExample2 {
    public static void main(String[] args) {
        Map<Student, Integer> map = new HashMap<>();

        map.put(new Student(1,"홍길동"), 95);
        map.put(new Student(1,"홍길동"), 95);

        System.out.println("총 Entry 수: " + map.size());
    }
}

class Student {
    public int sno;
    public String name;

    public Student(int sno, String name) {
        this.sno = sno;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student) {
            Student student = (Student) obj;
            return (sno==student.sno) && (name.equals(student.name));
        } else {
            return false;
        }
    }

    public int hashCode() {
        return sno+name.hashCode(); // 이 클래스의 논리적 동등 판단 기준은 `학번이름` 이다. 둘 중 하나라도 다르면 다른 객체로 판단
    }
}