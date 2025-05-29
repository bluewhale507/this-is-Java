package sec15_IOAndNetworking.example.subStream.Serialization;

import java.io.IOException;
import java.io.Serializable;

public class Child extends Parent implements Serializable {
    public String field2;

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeUTF(field1);
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        field1 = in.readUTF();
        in.defaultReadObject();
    }
}
