package sec15_IOAndNetworking.example.subStream.Serialization;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SerialVersionUIDExample2 {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("//C:/Users/Eunchan/IdeaProjects/this_is_java/src/sec15_IOAndNetworking/example/subStream/Object.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ClassC classC = (ClassC) ois.readObject();
        System.out.println("field1: " + classC.field1);
    }
}
