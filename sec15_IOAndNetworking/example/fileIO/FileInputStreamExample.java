package sec15_IOAndNetworking.example.fileIO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInputStreamExample {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\Eunchan\\IdeaProjects\\this_is_java\\src\\sec15_IOAndNetworking\\example\\fileIO\\FileInputStreamExample.java");

            int data;
            while((data = fis.read()) != -1 ){
                System.out.write(data); // console에 byte를 인코딩없이 바로 출력
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}