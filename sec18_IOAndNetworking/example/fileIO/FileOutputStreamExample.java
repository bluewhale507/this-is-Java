package sec18_IOAndNetworking.example.fileIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileOutputStreamExample {
    public static void main(String[] args) throws Exception {
        String originalFileName = "C:\\Users\\Eunchan\\IdeaProjects\\this_is_java\\src\\sec15_IOAndNetworking\\img\\example.png";
        String targetFileName = "C:\\Users\\Eunchan\\IdeaProjects\\this_is_java\\src\\sec15_IOAndNetworking\\img\\target.png";

        FileInputStream fis = new FileInputStream(originalFileName);
        FileOutputStream fos = new FileOutputStream(targetFileName);

        int readByteNo;
        byte[] readBytes = new byte[100];
        while ((readByteNo = fis.read(readBytes)) != -1) {
            fos.write(readBytes, 0, readByteNo);
        }

        fos.flush();
        fos.close();
        fos.close();

        System.out.println("복사가 잘 되었습니다.");
    }
}