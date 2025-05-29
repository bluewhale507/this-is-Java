package sec15_IOAndNetworking.example.fileIO;

import java.io.FileReader;
import java.io.IOException;

public class FileReaderExample {
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("C:\\Users\\Eunchan\\IdeaProjects\\this_is_java\\src\\sec15_IOAndNetworking\\example\\fileIO\\FileReaderExample.java");

        int readCharNo;
        char[] cbuf = new char[100];
        while( (readCharNo = fr.read(cbuf)) != -1 ) {
            String data = new String(cbuf, 0, readCharNo);
            System.out.print(data);
        }
        fr.close();
    }
}
