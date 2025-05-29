package sec15_IOAndNetworking.example.subStream.Serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NonSerializableParentExample {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("//C:/Users/Eunchan/IdeaProjects/this_is_java/src/sec15_IOAndNetworking/example/subStream/Object.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        Child child = new Child();
        child.field1 = "홍길동";
        child.field2 = "홍상원";
        oos.writeObject(child);
        oos.flush(); oos.close(); fos.close();

        FileInputStream fis = new FileInputStream("//C:/Users/Eunchan/IdeaProjects/this_is_java/src/sec15_IOAndNetworking/example/subStream/Object.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Child v = (Child) ois.readObject();
        System.out.println("field1: " + v.field1);
        System.out.println("field2: " + v.field2);
        ois.close(); fis.close();
    }
}
