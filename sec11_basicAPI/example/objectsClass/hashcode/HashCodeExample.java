package sec11_basicAPI.example.objectsClass.hashcode;

import java.util.Objects;

public class HashCodeExample {
    public static void main(String[] args) {
        Student s1 = new Student(1,"홍길동");
        Student s2 = new Student(1,"홍길동");
        System.out.println(s1.hashCode());
        System.out.println(Objects.hashCode(s2));
    }

    static class Student {
        int sno;
        String name;
        Student(int sno, String name) {
            this.sno = sno;
            this.name = name;
        }

        @Override
        public int hashCode() {
        	//sno, name 둘다 같아야 논리적으로 같은 객체로 판단하도록 하기위한 객체해시코드.
            return Objects.hash(sno,name);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Student) {
                Student s = (Student) obj;
                if(s.sno == this.sno) {
                    if(s.name.equals(this.name)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
