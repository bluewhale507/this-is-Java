package sec16_streamIterator.example.Collect;

import java.util.ArrayList;
import java.util.List;

public class MaleStudent {
    private List<Student$1> list;

    public MaleStudent() {
        list = new ArrayList<Student$1>();
        System.out.println("[" + Thread.currentThread().getName() + "] MaleStudent()");
    }

    public void accumulate(Student$1 student) {
        list.add(student);
        System.out.println("[" + Thread.currentThread().getName() + "] accumlate()");
    }

    public void combine(MaleStudent other) {
        list.addAll(other.getList());
        System.out.println("[" + Thread.currentThread().getName() + "] combine()");
    }

    public List<Student$1> getList() {
        return list;
    }
}
