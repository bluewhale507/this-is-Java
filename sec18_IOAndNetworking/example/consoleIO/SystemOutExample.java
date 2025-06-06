package sec18_IOAndNetworking.example.consoleIO;

import java.io.OutputStream;

public class SystemOutExample {
    public static void main(String[] args) throws Exception {
        OutputStream os = System.out;

        // 아스키 코드 48~57 출력
        for(byte b=48; b<58; b++) {
            os.write(b);
        }
        os.write(10);   // 라인피드(10)을 출력하면 다음 행으로 넘어간다.

        for(byte b=97; b<123; b++) {
            os.write(b);
        }
        os.write(10);   // 라인피드(10)을 출력하면 다음 행으로 넘어간다.

        String hangul = "가나다라마바사아자차카타파하";
        byte [] hangulBytes = hangul.getBytes();
        os.write(hangulBytes);

        os.flush();
    }
}
