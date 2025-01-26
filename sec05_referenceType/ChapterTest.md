## 확인문제
### 1. 참조 타입에 대한 설명으로 틀린것은 무엇입니까?
① 참조 타입에는 배열, 열거, 클래스, 인터페이스가 있다.
② 참조 타입 변수의 메모리 생성 위치는 스택이다.
③ 참조 타입에서 ==, != 연산자는 객체 번지를 비교한다.
④ 참조 타입은 null 값으로 초기화할 수 있다.

답 : ② 참조 타입 변수의 메모리 할당 위치는 heap 영역이다.

### 2. 자바에서 메모리 사용에 대한 설명으로 틀린것은 무엇입니까?
① 로컬 변수는 스택 영역에 생성되며, 실행 블록이 끝나면 소멸된다.
② 메소드 코드나, 상수, 열거 상수는 정적(메소드) 영역에 생성된다.
③ 참조되지 않는 객체는 프로그램에서 직접 소멸 코드를 작성하는 것이 좋다.
④ 배열 및 객체는 힙 영역에 생성된다.

답 : ③ 자바는 JVM의 GC가 메모리의 할당 및 해제를 처리하므로, 특수한 경우를 제외하고는 개발자가 관여하지 않는것이 바람직하다.  
(개발자가 작성하게 되면 메모리 누수 위험성과 복잡성이 증가하고, 모든 소스에서의 메모리 관리 방식을 GC(GarbageCollection)이 하는것 처럼 표준화하여 획일적으로 관리하는것 또한 상당한 비용이 들기 때문에 대부분의 상황에서 메모리관리에 직접 관여하지 않는것이 바람직하다.)

### 3. String 타입에 대한 설명으로 틀린것은 무엇입니까?
① String은 클래스이므로 참조타입이다.  
② String 타입의 문자열 비교는 ==를 사용해야 한다.  
③ 동일한 문자열 리터럴을 저장하는 변수는 동일한 String 객체를 참조한다.  
④ new String("문자열")은 문자열이 동일하더라도 다른 String 객체를 생성한다.  

답 : ② String 타입의 비교는 java.lang.String 클래스에서 재정의 한 java.lang.Object 클래스의 equals( ) 메서드를 이용해 클래스 내부의 char[ ]을 비교하여 동일한 시퀀스를 가지는지 확인한다.  

### 4. 배열을 생성하는 방법으로 틀린 것은 무엇입니까?
① int[ ] array = {1,2,3};  
② int[ ] array; array = {1,2,3};  
③ int[ ] array; new int[3];  
④ int[ ][ ] array = new int[3][2];  

답 : ② 값 목록으로 배열을 생성하는 경우 배열변수를 미리 선언한 후 다른 실행문에서 값 목록을 할당하는 방식은 허용되지 않는다.  

### 5. 배열의 기본 초기값에 대한 설명으로 틀린것은 무엇입니까?
① 정수타입 배열 항목의 기본 초기값은 0이다.  
② 실수타입 배열 항목의 기본 초기값은 0.0f 또는 0.0이다.  
③ boolean 타입 배열 항목의 기본 초기값은 true이다.  
④ 참조 타입 배열 항목의 기본 초기값은 null이다.  

답 : ③ boolean 타입의 기본 초기값은 false이다.

### 6. 배열의 길이에 대한 문제입니다. array.length의 값과 array[2].length의 값은 얼마입니까?
```java
int[][]array = {
        {95, 86},
        {83, 92, 96},
        {78, 83, 93, 87, 88}
};
```
정답 : 3, 5

### 7. 주어진 배열의 항목에서 최대값을 구해보세요(for문 이용)
```java
public class Exercise07 {
    public static void main(String[] args) {
        int max = 0;
        int[] array = {1, 5, 3, 8, 2};

        // 답
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) max = array[i];
        }
        
        System.out.println("최대값 : " + max);
    }
}
```

### 8. 주어진 배열의 전체 항목의 합과 평균값을 구해보세요 (중첩 for문 이용)
```java
public class Exercise08 {
    public static void main(String[] args) {
        int[][] array = {
                {95, 86},
                {83, 92, 96},
                {78, 83, 93, 87, 88}
        };
        
        int sum = 0;
        int cnt = 0;
        double avg = 0.0;
        
        // 답
        for(int[] row : array) {
            for(int element : row) {
                sum += element;
                cnt ++;
            }
        }
        avg = (double) sum / cnt;
        
        System.out.println("sum: "+sum);
        System.out.println("avg: "+avg);
    }
}
```

### 9. 다음은 키보드로부터 학생 수와 각 학생들의 점수를 입력받아 최고점수 및 평균점수를 구하는 프로그램입니다. 실행 결과를 보고 알맞게 작성하세요.
```java
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        boolean run = true;
        int studentNum = 0;
        int[] scores = null;
        Scanner scanner = new Scanner(System.in);

        while(run) {
            System.out.println("-------------------------------------------------------------");
            System.out.println("1.학생수 | 2.점수입력 | 3.점수리스트 | 4.분석 | 5.종료");
            System.out.println("-------------------------------------------------------------");
            System.out.print("선택> ");

            int selectNo = scanner.nextInt();

            if(selectNo == 1) {
                // 작성 위치
                System.out.print("학생수> ");
                studentNum = scanner.nextInt();
                scores = new int[studentNum];
            } else if(selectNo == 2) {
                // 작성 위치
                for(int i=0; i<scores.length; i++) {
                    System.out.print("scores["+i+"] : ");
                    scores[i] = scanner.nextInt();
                }
            } else if(selectNo == 3) {
                // 작성 위치
                for(int i=0; i<scores.length; i++) {
                    System.out.println("scores["+i+"] = "+scores[i]);
                }
            } else if(selectNo == 4) {
                // 작성 위치
                int max = 0;
                int sum = 0;
                double avg = 0.0;

                for(int score : scores) {
                    if( score > max ) max = score;
                    sum += score;
                }

                avg = (double) sum / scores.length;

                System.out.println("최고 점수: "+max);
                System.out.println("평균 점수: " + avg);
            } else if(selectNo == 5) {
                // 작성 위치
                run = false;
            }
        }

        System.out.println("프로그램 종료");
    }
}
```