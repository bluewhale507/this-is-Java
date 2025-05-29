package sec15_IOAndNetworking.example.subStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class BufferedInputStreamExample {
    public static void main(String[] args) throws Exception {
        long start = 0;
        long end = 0;

        FileInputStream fis1 = new FileInputStream("C:\\Users\\Eunchan\\IdeaProjects\\this_is_java\\src\\sec15_IOAndNetworking\\img\\example.png");
        start = System.currentTimeMillis();
        while(fis1.read() != -1) {}
        end = System.currentTimeMillis();
        System.out.println("사용하지 않았을 때: " + (end - start) + "ms");
        fis1.close();

        FileInputStream fis2 = new FileInputStream("C:\\Users\\Eunchan\\IdeaProjects\\this_is_java\\src\\sec15_IOAndNetworking\\img\\example.png");
        BufferedInputStream bis = new BufferedInputStream(fis2);
        start = System.currentTimeMillis();
        while(bis.read() != -1) {}
        end = System.currentTimeMillis();
        System.out.println("사용했을 때: " + (end - start) + "ms");
        bis.close();
        fis1.close();
    }
}

