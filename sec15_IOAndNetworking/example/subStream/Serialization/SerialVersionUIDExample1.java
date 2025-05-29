package sec15_IOAndNetworking.example.subStream.Serialization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SerialVersionUIDExample1 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("//C:/Users/Eunchan/IdeaProjects/this_is_java/src/sec15_IOAndNetworking/example/subStream/Object.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        ClassC classC = new ClassC();
        classC.field1 = 1;
        oos.writeObject(classC);
        oos.close(); oos.close(); fos.close();
    }
}
