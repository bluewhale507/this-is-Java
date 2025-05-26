package sec11_basicAPI.example.SystemExample;

import java.util.Properties;
import java.util.Set;

public class GetPropertyExample {
        public static void main(String[] args) {
            String osName = System.getProperty("os.name");
            String userName = System.getProperty("user.name");
            String userHome = System.getProperty("user.home");

            System.out.println("운영체제 이름 : " + osName);
            System.out.println("사용자 이름 : " + userName);
            System.out.println("사용자 홈 디렉토리 : " + userHome);

            System.out.println("--------------------------");
            System.out.println(" [ key ] value");
            System.out.println("--------------------------");

            Properties props = System.getProperties();      // 모든 키,값 쌍을 가진 Properties 객체를 리턴
            Set Keys = props.keySet();                      // 키만으로 구성된 set 객체를 얻음
            for(Object objkey : Keys) {
                String key = (String) objkey;
                String value = System.getProperty(key);
                System.out.println("[ " + key + " ] " + value);
            }
    }
}